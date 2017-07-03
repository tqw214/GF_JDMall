package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public abstract class JdBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;

    public JdBaseAdapter(Context ctx) {
        this.mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getViewBase(i,view,viewGroup);
    }

    protected abstract View getViewBase(int i, View view, ViewGroup viewGroup);

}
