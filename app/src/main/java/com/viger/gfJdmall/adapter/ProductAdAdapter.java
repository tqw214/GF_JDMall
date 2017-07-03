package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.cons.NetworkConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ProductAdAdapter extends PagerAdapter {

    private List<String> mImgUrls;
    private Context mContext;
    private List<SmartImageView> mImageViews;

    public ProductAdAdapter(Context ctx) {
        this.mContext = ctx;
    }

    public void setData(List<String> data) {
        this.mImgUrls = data;
        mImageViews = new ArrayList<SmartImageView>();
        for (String s : data) {
            SmartImageView iv = new SmartImageView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setImageUrl(NetworkConst.BASE_URL + s);
            mImageViews.add(iv);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageViews != null ? mImageViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SmartImageView iv = mImageViews.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((SmartImageView)object);
    }

}
