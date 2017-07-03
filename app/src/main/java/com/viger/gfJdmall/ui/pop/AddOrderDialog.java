package com.viger.gfJdmall.ui.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.AddOrderResultBean;

/**
 * Created by Administrator on 2017/6/26.
 */

public class AddOrderDialog extends AlertDialog {

    private Button sure_btn;
    private TextView actual_price_tv,freight_tv,total_price_tv,order_no_tv;
    private AddOrderResultBean data;

    public AddOrderDialog(Context context, AddOrderResultBean bean) {
        super(context, R.style.CustomDialog);
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
            }
        });
    }
}
