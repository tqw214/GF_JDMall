package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.RecommendBean;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.NetworkConst;

/**
 * Created by Administrator on 2017/5/12.
 */

public class RecommendAdapter extends JdBaseAdapter<RecommendBean> {

    public RecommendAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.recommend_gv_item, viewGroup, false);
            holder = new ViewHolder();
            holder.imageView = (SmartImageView) view.findViewById(R.id.image_iv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.price_tv = (TextView) view.findViewById(R.id.price_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        RecommendBean rowsBean = mDatas.get(i);
        holder.imageView.setImageUrl(NetworkConst.BASE_URL + rowsBean.getIconUrl());
        holder.name_tv.setText("¥ " + rowsBean.getName());
        holder.price_tv.setText(" ¥ " + rowsBean.getPrice() + " ");
        return view;
    }

    class ViewHolder {
        SmartImageView imageView;
        TextView name_tv;
        TextView price_tv;
    }
}
