package com.viger.gfJdmall.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.CategoryBean;

/**
 * Created by Administrator on 2017/5/17.
 */

public class TopCategoryAdapter extends JdBaseAdapter<CategoryBean> {

    private int mCurrentPosition = 0; //默认选中第一个

    public TopCategoryAdapter(Context ctx) {
        super(ctx);
    }

    public void setmCurrentPosition(int m) {
        this.mCurrentPosition = m;
        this.notifyDataSetChanged();
    }

    @Override
    protected View getViewBase(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.top_category_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.name_tv);
            holder.divider = view.findViewById(R.id.divider);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        CategoryBean categoryBean = mDatas.get(i);
        holder.tv.setText(categoryBean.getName());
        if(mCurrentPosition == i) {
            holder.tv.setSelected(true);
            holder.divider.setVisibility(View.INVISIBLE);
            holder.tv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
        }else {
            holder.tv.setSelected(false);
            holder.divider.setVisibility(View.VISIBLE);
            holder.tv.setBackgroundResource(R.color.transparent);
        }
        return view;
    }

    class ViewHolder {
        View divider;
        TextView tv;
    }



}
