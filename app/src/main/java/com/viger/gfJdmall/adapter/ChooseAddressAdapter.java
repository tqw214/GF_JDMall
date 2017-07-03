package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.AdressBean;
import com.viger.gfJdmall.bean.GoodCommentBean;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.ui.RatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class ChooseAddressAdapter extends JdBaseAdapter<AdressBean> {

    private OnDeleteAddressListener mListener;

    public void setOnDeleteAddressListener(OnDeleteAddressListener listener) {
        this.mListener = listener;
    }

    public interface OnDeleteAddressListener {
        void onDelete(int position);
    }

    public ChooseAddressAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.choose_address_item_view, viewGroup, false);
            holder = new ViewHolder();
            holder.isDeafult_iv = (ImageView) view.findViewById(R.id.isDeafult_iv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.phone_tv = (TextView) view.findViewById(R.id.phone_tv);
            holder.address_tv = (TextView) view.findViewById(R.id.address_tv);
            holder.delete_tv = (TextView) view.findViewById(R.id.delete_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        AdressBean rowsBean = mDatas.get(i);
        holder.isDeafult_iv.setVisibility(rowsBean.isDefault() ? View.VISIBLE : View.INVISIBLE);
        holder.name_tv.setText(rowsBean.getReceiverName());
        holder.phone_tv.setText(rowsBean.getReceiverPhone());
        holder.address_tv.setText(rowsBean.getReceiverAddress());
        holder.delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDelete(i);
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView isDeafult_iv;
        TextView name_tv;
        TextView phone_tv;
        TextView address_tv;
        TextView delete_tv;
    }
}
