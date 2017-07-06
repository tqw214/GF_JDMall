package com.viger.gfJdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.AddOrderBean;
import com.viger.gfJdmall.bean.AddOrderResultBean;
import com.viger.gfJdmall.bean.AdressBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.ShopCarBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.pop.AddOrderDialog;
import com.viger.gfJdmall.ui.pop.PayWhenGetDialog;
import com.viger.gfJdmall.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SettleActivity extends BaseActivity implements IModeChangeListener{

    private RelativeLayout has_receiver_rl,no_receiver_rl;
    private TextView name_tv,phone_tv,address_tv;
    private TextView choose_address_tv;
    private TextView choose_address_tv2;
    private List<ShopCarBean> mShopCarBean;
    private LinearLayout product_container_ll;
    private TextView total_psize_tv,all_price_val_tv;
    private TextView pay_money_tv;
    private Button pay_online_tv,pay_whenget_tv;
    private AdressBean mAdressBean;
    private AddOrderBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        initData();
        initUI();
        initController();
    }

    private void initData() {
        Intent intent = getIntent();
        mShopCarBean = (List<ShopCarBean>) intent.getSerializableExtra("data");
    }

    @Override
    protected void initController() {
        super.initController();
        mController = new ShopCarController(this);
        mController.setModeChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.RECEIVE_DEFAULT_ADDRESS, getUserId(), true);
    }

    protected long getUserId() {
        return ((MyApplication)getApplication()).getUserInfo().getId();
    }

    private void resetButton(View v) {
        pay_online_tv.setSelected(v.getId() == R.id.pay_online_tv);
        pay_whenget_tv.setSelected(v.getId() == R.id.pay_whenget_tv);
    }

    private void initUI() {
        pay_online_tv = (Button) findViewById(R.id.pay_online_tv);
        pay_online_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButton(view);
            }
        });
        pay_whenget_tv = (Button) findViewById(R.id.pay_whenget_tv);
        pay_whenget_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButton(view);
            }
        });
        pay_money_tv = (TextView) findViewById(R.id.pay_money_tv);
        total_psize_tv = (TextView) findViewById(R.id.total_psize_tv);
        all_price_val_tv = (TextView) findViewById(R.id.all_price_val_tv);
        has_receiver_rl = (RelativeLayout) findViewById(R.id.has_receiver_rl);
        no_receiver_rl = (RelativeLayout) findViewById(R.id.no_receiver_rl);
        name_tv = (TextView) findViewById(R.id.name_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        address_tv = (TextView) findViewById(R.id.address_tv);
        choose_address_tv = (TextView) findViewById(R.id.choose_address_tv);
        choose_address_tv2 = (TextView) findViewById(R.id.choose_address_tv2);
        product_container_ll = (LinearLayout) findViewById(R.id.product_container_ll);
        int count = Math.min(product_container_ll.getChildCount(), mShopCarBean.size());
        for(int i=0;i<count;i++) {
            LinearLayout ll = (LinearLayout) product_container_ll.getChildAt(i);
            SmartImageView iv = (SmartImageView) ll.getChildAt(0);
            TextView tv = (TextView) ll.getChildAt(1);
            iv.setImageUrl(NetworkConst.BASE_URL + mShopCarBean.get(i).getPimageUrl());
            tv.setText("X " + mShopCarBean.get(i).getBuyCount());
        }
        float totalPrice = 0;
        for(ShopCarBean bean : mShopCarBean) {
            totalPrice += bean.getPprice();
        }
        total_psize_tv.setText("共"+mShopCarBean.size()+"件");
        all_price_val_tv.setText(totalPrice+"");
        pay_money_tv.setText("实付款: ¥" + totalPrice);
    }


    public void submitClick(View v) {
        if(!pay_online_tv.isSelected() && !pay_whenget_tv.isSelected()) {
            tip("请选择支付方式");
            return;
        }
//        if(pay_online_tv.isSelected()) {
//            tip("在线支付");
//        }else {
//            tip("货到付款");
//        }
        if(mAdressBean == null) {
            tip("请选择或添加收货地址");
            return;
        }
        bean = new AddOrderBean();
        bean.setAddrId(mAdressBean.getId());
        bean.setPayWay(pay_online_tv.isSelected()? 0:1);
        bean.setUserId(getUserId());
        List<AddOrderBean.Product> productList = new ArrayList<AddOrderBean.Product>();
        for(ShopCarBean data : mShopCarBean) {
            AddOrderBean.Product product = new AddOrderBean.Product();
            product.setPid(data.getPid());
            product.setBuyCount(data.getBuyCount());
            product.setType(data.getPversion());
            productList.add(product);
        }
        bean.setProducts(productList);
        String params = JSON.toJSON(bean).toString();
        mController.sendAsyncMessage(IdiyMessage.ADD_ORDER, params);
    }

    //选择收货地址
    public void chooseAddress(View view) {
        tip("选择收货地址");
        Intent intent = new Intent(this, ChooseAddressActivity.class);
        startActivityForResult(intent, 1);
    }

    //添加新地址
    public void addAddress(View view) {
        tip("添加新地址");
        Intent intent = new Intent(this, AddReciverActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(data != null) {
                mAdressBean = (AdressBean) data.getSerializableExtra("data");
                handleDefaultReceiveAddress(mAdressBean);
            }
        }
        if(requestCode == 1) {
            if(data != null) {
                mAdressBean = (AdressBean) data.getSerializableExtra("data");
                handleDefaultReceiveAddress(mAdressBean);
            }
        }
    }

    @Override
    protected void initView() {

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
                case IdiyMessage.RECEIVE_DEFAULT_ADDRESS:
                    handleDefaultReceiveAddress((AdressBean) msg.obj);
                    break;
                case IdiyMessage.ADD_ORDER:
                    handleAddOrder((RResult) msg.obj);
                    break;
            }
        }
    };

    private void handleAddOrder(RResult result) {
        if(result != null) {
            if(result.isSuccess()) {
                AddOrderResultBean data = JSON.parseObject(result.getResult(), AddOrderResultBean.class);
                int errorType = data.getErrorType();
                if(errorType == 0) {
                    tip("下单成功");

                    int payWay = bean.getPayWay();
                    if(payWay == 0) {
                        //在线支付
                        PayWhenGetDialog pDialog = new PayWhenGetDialog(this, data);
                        pDialog.show();
                    }else {
                        //货到付款
                        AddOrderDialog addOrderDialog = new AddOrderDialog(this, data);
                        addOrderDialog.show();
                    }
                }
                if(errorType == 1) {
                    tip("无库存");
                }
                if(errorType == 2) {
                    tip("系统失败");
                }
            }else {
                tip("下单失败:"+result.getErrorMsg());
            }
        }
    }

    private void handleDefaultReceiveAddress(AdressBean data) {
        no_receiver_rl.setVisibility(data != null ? View.GONE : View.VISIBLE);
        has_receiver_rl.setVisibility(data != null ? View.VISIBLE : View.GONE);
        if(data != null) {
            tip(data.getReceiverAddress());
            name_tv.setText(data.getReceiverName());
            phone_tv.setText(data.getReceiverPhone());
            address_tv.setText(data.getReceiverAddress());
            mAdressBean = data;
        }
    }

    public void goBack(View view) {
        finish();
    }
}
