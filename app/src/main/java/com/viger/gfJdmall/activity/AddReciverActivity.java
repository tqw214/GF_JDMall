package com.viger.gfJdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.AdressBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.ReceviceAdressBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.pop.ChooseAreaPopWindow;

/**
 * Created by Administrator on 2017/6/19.
 */

public class AddReciverActivity extends BaseActivity implements IModeChangeListener{

    private LinearLayout parent_view;
    private EditText name_et,phone_et,address_details_et;
    private TextView choose_province_tv;
    private CheckBox default_cbx;
    private ChooseAreaPopWindow mPopWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiver);
        initView();
    }

    @Override
    protected void initView() {
        parent_view = (LinearLayout) findViewById(R.id.parent_view);
        name_et = (EditText) findViewById(R.id.name_et);
        phone_et = (EditText) findViewById(R.id.phone_et);
        choose_province_tv = (TextView) findViewById(R.id.choose_province_tv);
        address_details_et = (EditText) findViewById(R.id.address_details_et);
        default_cbx = (CheckBox) findViewById(R.id.default_cbx);
        initController();
    }

    @Override
    protected void initController() {
        super.initController();
        mController = new ShopCarController(this);
        mController.setModeChangeListener(this);
    }

    public void goBack(View v) {

    }

    protected long getUserId() {
        return ((MyApplication)getApplication()).getUserInfo().getId();
    }

    public void saveAddress(View v) {
        long userId = getUserId();
        String name = name_et.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            tip("用户名不能为空");
            return;
        }
        String phone = phone_et.getText().toString().trim();
        if(TextUtils.isEmpty(phone)) {
            tip("手机号码不能为空");
            return;
        }
        String adress = address_details_et.getText().toString().trim();
        if(TextUtils.isEmpty(adress)) {
            tip("详细地址不能为空");
            return;
        }
        if(mProvinceBean == null || mCityBean == null || mAreaBean == null) {
            tip("请选择省市区");
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.ADD_ADDRESS, userId, name, phone, mProvinceBean.getCode(), mCityBean.getCode(),
                mAreaBean.getCode(), adress, default_cbx.isChecked());
    }

    private ReceviceAdressBean mProvinceBean,mCityBean,mAreaBean;

    public void reGetAddress(View v) {
        if(mPopWin == null) {
            mPopWin = new ChooseAreaPopWindow(this);
            mPopWin.setOnAdressChangeListener(new ChooseAreaPopWindow.IAdressChangeListener() {
                @Override
                public void onAdressChange(ReceviceAdressBean v1, ReceviceAdressBean v2, ReceviceAdressBean v3) {
                    mProvinceBean = v1;
                    mCityBean = v2;
                    mAreaBean = v3;
                    choose_province_tv.setText(v1.getName()+v2.getName()+v3.getName());
                    mPopWin.onDismiss();
                }
            });
        }
        mPopWin.onShow(parent_view);
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
                case IdiyMessage.ADD_ADDRESS:
                    handleAddAddress((RResult) msg.obj);
                    break;
            }
        }
    };

    private void handleAddAddress(RResult bean) {
        if(bean.isSuccess()) {
            AdressBean data = JSON.parseObject(bean.getResult(), AdressBean.class);
            tip("添加成功:"+data.getReceiverAddress());
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(0,intent);
            finish();
        }else {
            tip("添加失败:"+bean.getErrorMsg());
        }


    }

}
