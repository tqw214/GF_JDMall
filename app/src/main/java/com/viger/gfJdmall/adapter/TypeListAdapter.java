package com.viger.gfJdmall.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.BrandBean;

/**
 * Created by Administrator on 2017/5/17.
 */

public class TypeListAdapter extends JdBaseAdapter<String> {

    private int mCurrentIndex = -1;

    public TypeListAdapter(Context ctx) {
        super(ctx);
    }

    public void setCurrentIndex(int position) {
        this.mCurrentIndex = position;
        this.notifyDataSetChanged();
    }

    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    @Override
    protected View getViewBase(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.brand_lv_item_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.brand_tv = (TextView) view.findViewById(R.id.brand_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        String type = mDatas.get(i);
        holder.brand_tv.setSelected(mCurrentIndex == i);
        holder.brand_tv.setText(type);
        return view;
    }

    class ViewHolder {
        TextView brand_tv;
    }



}
