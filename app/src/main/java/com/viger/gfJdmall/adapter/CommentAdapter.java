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
import com.viger.gfJdmall.bean.CommentDetailBean;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.ui.RatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class CommentAdapter extends JdBaseAdapter<CommentDetailBean> {

    public CommentAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getViewBase(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.comment_item_view, viewGroup, false);
            holder = new ViewHolder();
            holder.icon_iv = (SmartImageView) view.findViewById(R.id.icon_iv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.time_tv = (TextView) view.findViewById(R.id.time_tv);
            holder.rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
            holder.content_tv = (TextView) view.findViewById(R.id.content_tv);
            holder.iamges_container = (LinearLayout) view.findViewById(R.id.iamges_container);
            holder.buytime_tv = (TextView) view.findViewById(R.id.buytime_tv);
            holder.buyversion_tv = (TextView) view.findViewById(R.id.buyversion_tv);
            holder.lovecount_tv = (TextView) view.findViewById(R.id.lovecount_tv);
            holder.subcomment_tv = (TextView) view.findViewById(R.id.subcomment_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        CommentDetailBean rowsBean = mDatas.get(i);
        holder.icon_iv.setImageUrl(NetworkConst.BASE_URL + rowsBean.getUserImg());
        holder.name_tv.setText(rowsBean.getUserName()+"");
        holder.time_tv.setText(rowsBean.getCommentTime()+"");
        holder.rating_bar.setRating(rowsBean.getRate());
        holder.content_tv.setText(rowsBean.getComment()+"");
        List<String> imgUrls = JSON.parseArray(rowsBean.getImgUrls(), String.class);
        if(imgUrls != null && imgUrls.size()>0) {
            holder.iamges_container.setVisibility(View.VISIBLE);
            int childCount = holder.iamges_container.getChildCount(); //4
            int count = Math.min(imgUrls.size(),childCount);
            for(int j=0;j<count;j++) {
                SmartImageView childAt = (SmartImageView) holder.iamges_container.getChildAt(j);
                childAt.setImageUrl(NetworkConst.BASE_URL + imgUrls.get(j));
            }
        }else {
            holder.iamges_container.setVisibility(View.GONE);
        }
        holder.buytime_tv.setText(rowsBean.getBuyTime()+"");
        holder.buyversion_tv.setText(rowsBean.getProductType()+"");
        holder.lovecount_tv.setText("喜欢("+rowsBean.getLoveCount()+")");
        holder.subcomment_tv.setText("回复("+rowsBean.getSubComment()+")");
        return view;
    }

    class ViewHolder {
        SmartImageView icon_iv;
        TextView name_tv;
        TextView time_tv;
        RatingBar rating_bar;
        TextView content_tv;
        LinearLayout iamges_container;
        TextView buytime_tv;
        TextView buyversion_tv;
        TextView lovecount_tv;
        TextView subcomment_tv;

    }
}
