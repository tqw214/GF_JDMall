package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.NetworkConst;

/**
 * Created by Administrator on 2017/5/12.
 */

public class SecondKillAdapter extends JdBaseAdapter<SecondKillBean.RowsBean> {

    public SecondKillAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.home_seckill_item, viewGroup, false);
            holder = new ViewHolder();
            holder.imageView = (SmartImageView) view.findViewById(R.id.image_iv);
            holder.pointPrice = (TextView) view.findViewById(R.id.nowprice_tv);
            holder.allPrice = (TextView) view.findViewById(R.id.normalprice_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        SecondKillBean.RowsBean rowsBean = mDatas.get(i);
        holder.imageView.setImageUrl(NetworkConst.BASE_URL + rowsBean.getIconUrl());
        holder.pointPrice.setText("¥ " + rowsBean.getPointPrice());
        holder.allPrice.setText(" ¥ " + rowsBean.getAllPrice() + " ");
        holder.allPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return view;
    }

    class ViewHolder {
        SmartImageView imageView;
        TextView pointPrice;
        TextView allPrice;
    }
}
