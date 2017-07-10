package com.viger.gfJdmall.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.MyOrderBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;

/**
 * Created by Administrator on 2017/5/24.
 */

public class AlipayPopWindow extends IPopupWindowProtocal implements View.OnClickListener, IModeChangeListener{

    private View mView;
    private EditText account_et;
    private EditText pwd_et;
    private EditText pay_pwd_et;
    private Button cancel_btn;
    private Button pay_btn;
    private ShopCarController mController;
    private String tn;
    private long userid;

    public AlipayPopWindow(Context ctx, String tn, long userid) {
        super(ctx);
        initUI();
        this.tn = tn;
        this.userid = userid;
    }

    public void initUI() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.alipay_pop_view, null);
        account_et = (EditText) mView.findViewById(R.id.account_et);
        pwd_et = (EditText) mView.findViewById(R.id.pwd_et);
        pay_pwd_et = (EditText) mView.findViewById(R.id.pay_pwd_et);
        cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);
        pay_btn = (Button) mView.findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);
        mPopWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setContentView(mView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.update();
        initController();
    }

    private void initController() {
        mController = new ShopCarController(mContext);
        mController.setModeChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.GET_PROVINCE);
    }

    @Override
    public void onShow(View view) {
        if(mPopWindow!=null) {
            mPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                onDismiss();
                ((Activity)mContext).finish();
                //提示去订单列表继续支付
                Toast.makeText(mContext, "请到订单列表中继续支付", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay_btn:
                Toast.makeText(mContext, "付款", Toast.LENGTH_SHORT).show();
                String name = account_et.getText().toString().trim();
                String pwd = pwd_et.getText().toString().trim();
                String pay_pwd = pay_pwd_et.getText().toString().trim();
                if(checkDataIsEmpty(name, pwd, pay_pwd)) {
                    mController.sendAsyncMessage(IdiyMessage.PAY, name, pwd, pay_pwd, tn, userid);
                    Toast.makeText(mContext, "模拟支付....", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkDataIsEmpty(String... value) {
        for(String s : value) {
            if(TextUtils.isEmpty(s)) {
                Toast.makeText(mContext, "数据必须完整", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandler.obtainMessage(action, data[0]).sendToTarget();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.PAY:
                    if(msg.obj != null) {
                        handlePay((RResult) msg.obj);
                    }
                    break;

            }
        }
    };

    private void handlePay(RResult result) {
        if(result.isSuccess()) {
            Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            MyOrderBean bean = JSON.parseObject(result.getResult(), MyOrderBean.class);

        }else {
            Toast.makeText(mContext, "支付失败:"+result.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }

}
