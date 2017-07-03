package com.viger.gfJdmall.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.BrandBean;
import com.viger.gfJdmall.bean.ProductListBean;
import com.viger.gfJdmall.cons.NetworkConst;

/**
 * Created by Administrator on 2017/5/17.
 */

public class ProductListAdapter extends JdBaseAdapter<ProductListBean> {


    public ProductListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.product_lv_item, viewGroup, false);
            holder = new ViewHolder();
            holder.product_iv = (SmartImageView) view.findViewById(R.id.product_iv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.commrate_tv = (TextView) view.findViewById(R.id.commrate_tv);
            holder.price_tv = (TextView) view.findViewById(R.id.price_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ProductListBean bean = mDatas.get(i);
        holder.product_iv.setImageUrl(NetworkConst.BASE_URL + bean.getIconUrl());
        holder.name_tv.setText(bean.getName());
        holder.commrate_tv.setText(bean.getCommentCount()+"条评价 好评率"+bean.getFavcomRate()+"%");
        holder.price_tv.setText("¥" + bean.getPrice());
        return view;
    }

    class ViewHolder {
        SmartImageView product_iv;
        TextView name_tv;
        TextView commrate_tv;
        TextView price_tv;

    }



}
