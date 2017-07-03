package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.OrderBean;
import com.viger.gfJdmall.cons.NetworkConst;

/**
 * Created by Administrator on 2017/5/12.
 */

public class WaitPayAdapter extends JdBaseAdapter<OrderBean> {

    private OnDeleteOrderListener mListener;

    public void setOnDeleteOrderListener(OnDeleteOrderListener listener) {
        this.mListener = listener;
    }

    public interface OnDeleteOrderListener {
        void onDelete(int position);
    }

    public WaitPayAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.order_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.order_no_tv = (TextView) view.findViewById(R.id.order_no_tv);
            holder.order_state_tv = (TextView) view.findViewById(R.id.order_state_tv);
            holder.p_container_ll = (LinearLayout) view.findViewById(R.id.p_container_ll);
            holder.price_tv = (TextView) view.findViewById(R.id.price_tv);
            holder.do_btn = (Button) view.findViewById(R.id.do_btn);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        OrderBean rowsBean = mDatas.get(i);
        holder.order_no_tv.setText("订单号:"+rowsBean.getOrderNum());
        holder.order_state_tv.setText(showOrderStatus(Integer.valueOf(rowsBean.getStatus())));
        holder.price_tv.setText("¥:"+rowsBean.getTotalPrice());
        holder.do_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDelete(i);
            }
        });
        int count = Math.min(holder.p_container_ll.getChildCount(), rowsBean.getItems().size());
        for (int m=0;m<holder.p_container_ll.getChildCount();m++) {
            holder.p_container_ll.getChildAt(m).setVisibility(View.INVISIBLE);
        }
        for(int j=0;j<count;j++) {
            holder.p_container_ll.getChildAt(j).setVisibility(View.VISIBLE);
            SmartImageView image = (SmartImageView) holder.p_container_ll.getChildAt(j);
            image.setImageUrl(NetworkConst.BASE_URL + rowsBean.getItems().get(j));
        }
        return view;
    }

    class ViewHolder {
        TextView order_no_tv;
        TextView order_state_tv;
        LinearLayout p_container_ll;
        TextView price_tv;
        Button do_btn;
    }

    private String showOrderStatus(int status) {
        switch (status) {
            case -1:
                return "取消订单";
            case 0:

                return "待支付";
            case 1:

                return "待发货";
            case 2:

                return "待收货";
            case 3:

                return "完成交易";
        }
        return "";
    }

}
