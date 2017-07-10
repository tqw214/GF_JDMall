package com.viger.gfJdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.AddOrderResultBean;
import com.viger.gfJdmall.bean.PayInfoBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.pop.AlipayPopWindow;

/**
 * Created by Administrator on 2017/7/5.
 */

public class AlipayActivity extends BaseActivity implements IModeChangeListener {

    private TextView pay_price_tv;
    private TextView order_desc_val_tv;
    private TextView deal_type_val_tv;
    private TextView deal_time_val_tv;
    private TextView deal_no_val_tv;
    private LinearLayout container;
    private AddOrderResultBean data;
    private  AlipayPopWindow alipayPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        data = (AddOrderResultBean) getIntent().getSerializableExtra("data");
        initView();
        initController();
    }

    @Override
    protected void initController() {
        super.initController();
        mController = new ShopCarController(this);
        mController.setModeChangeListener(this);
       mController.sendAsyncMessage(IdiyMessage.GET_PAY_INFO, getUserId(), data.getTn());
    }

    protected long getUserId() {
        return ((MyApplication)getApplication()).getUserInfo().getId();
    }

    @Override
    protected void initView() {
        pay_price_tv = (TextView) findViewById(R.id.pay_price_tv);
        order_desc_val_tv = (TextView) findViewById(R.id.order_desc_val_tv);
        deal_type_val_tv = (TextView) findViewById(R.id.deal_type_val_tv);
        deal_time_val_tv = (TextView) findViewById(R.id.deal_time_val_tv);
        deal_no_val_tv = (TextView) findViewById(R.id.deal_no_val_tv);
        container = (LinearLayout) findViewById(R.id.container);
    }

    public void payClick(View view) {
        Toast.makeText(this, "立即付款", Toast.LENGTH_SHORT).show();
        if(alipayPopWindow == null) {
            alipayPopWindow = new AlipayPopWindow(this, data.getTn(), getUserId());
        }
        alipayPopWindow.onShow(container);
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandle.obtainMessage(action,data[0]).sendToTarget();
    }

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.GET_PAY_INFO:
                    PayInfoBean bean = (PayInfoBean) msg.obj;
                    handleGetPayInfo(bean);
                    break;
            }
        }
    };

    private void handleGetPayInfo(PayInfoBean bean) {
        pay_price_tv.setText("¥ "+bean.getTotalPrice()+"");
        order_desc_val_tv.setText(bean.getOinfo());
        //deal_type_val_tv.setText(data.getPayWay());
        deal_time_val_tv.setText(bean.getPayTime());
        deal_no_val_tv.setText(bean.getTn());
    }
}
