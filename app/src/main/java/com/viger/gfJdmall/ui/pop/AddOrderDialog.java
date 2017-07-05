package com.viger.gfJdmall.ui.pop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.AddOrderResultBean;
import com.viger.gfJdmall.utils.NetworkUtil;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Administrator on 2017/6/26.
 */

public class AddOrderDialog extends AlertDialog {

    private Button sure_btn;
    private TextView actual_price_tv,freight_tv,total_price_tv,order_no_tv,order_info;
    private AddOrderResultBean data;
    private Activity act;

    public AddOrderDialog(Context context, AddOrderResultBean bean) {
        super(context, R.style.CustomDialog);
        this.act = (Activity) context;
        this.data = bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_order_pop_view);
        Button btn_sure = (Button) findViewById(R.id.sure_btn);
        TextView orderId = (TextView) findViewById(R.id.order_no_tv);
        orderId.setText("订单编号:"+data.getOrderNum());
        TextView price = (TextView) findViewById(R.id.total_price_tv);
        price.setText("总价:¥ "+data.getAllPrice()+"");
        TextView freight = (TextView) findViewById(R.id.freight_tv);
        freight.setText("运费:¥ "+data.getFreight()+"");
        TextView sf = (TextView) findViewById(R.id.actual_price_tv);
        sf.setText("实付:¥ "+data.getTotalPrice());
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOrderDialog.this.dismiss();
                act.finish();
            }
        });
        TextView order_info = (TextView) findViewById(R.id.order_info);
        order_info.setText("订单信息: uId="+getUserId() + " oId=" + data.getOid());
        Button send = (Button) findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("userId",getUserId()+"");
                        params.put("oid",data.getOid()+"");
                        NetworkUtil.doGet("http://mall.520it.com/deliver", params);
                        mHandle.sendEmptyMessage(0);
                    }
                }.start();
            }
        });
    }

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(act, "发货完成", Toast.LENGTH_SHORT).show();
        }
    };

    private long getUserId() {
        return ((MyApplication)act.getApplication()).getUserInfo().getId();
    }

}
