package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.GoodCommentBean;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.ui.RatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class GoodCommentAdapter extends JdBaseAdapter<GoodCommentBean> {

    public GoodCommentAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.recommend_comment_item_view, viewGroup, false);
            holder = new ViewHolder();
            holder.rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.content_tv = (TextView) view.findViewById(R.id.content_tv);
            holder.iamges_container = (LinearLayout) view.findViewById(R.id.iamges_container);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        GoodCommentBean rowsBean = mDatas.get(i);
        holder.rating_bar.setRating(rowsBean.getRate());
        holder.name_tv.setText(rowsBean.getUserName());
        holder.content_tv.setText(rowsBean.getComment());

        List<String> imageUrls = JSON.parseArray(rowsBean.getImgUrls(), String.class);
        if(imageUrls != null && imageUrls.size() > 0) {
            holder.iamges_container.setVisibility(View.VISIBLE);
            int imageCount = imageUrls.size();
            for(int j=0;j<imageCount;j++) {
                SmartImageView child = (SmartImageView) holder.iamges_container.getChildAt(j);
                child.setImageUrl(NetworkConst.BASE_URL + imageUrls.get(j));
            }
        }else {
            holder.iamges_container.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        RatingBar rating_bar;
        TextView name_tv;
        TextView content_tv;
        LinearLayout iamges_container;
    }
}
