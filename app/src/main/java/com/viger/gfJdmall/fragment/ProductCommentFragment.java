package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.CommentAdapter;
import com.viger.gfJdmall.bean.CommentCountBean;
import com.viger.gfJdmall.bean.CommentDetailBean;
import com.viger.gfJdmall.bean.GoodCommentBean;
import com.viger.gfJdmall.bean.ProductInfoBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ProductController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.FlexiListView;
import com.viger.gfJdmall.ui.MyListView;

import java.util.List;

/**
 * Created by lean on 16/10/28.
 */

public class ProductCommentFragment extends Fragment implements View.OnClickListener,IModeChangeListener {

    private View mView;
    private LinearLayout mAll,mGood,mCenter,mBad,mHasImg;
    private TextView all_comment_tip,positive_comment_tip,center_comment_tip,nagetive_comment_tip,has_image_comment_tip;
    private TextView all_comment_tv,positive_comment_tv,center_comment_tv,nagetive_comment_tv,has_image_comment_tv;
    private ProductController mController;
    private long pId;
    private FlexiListView comment_lv;
    private CommentAdapter mAdapter;
    public static final int ALL_COMMENT = 0;
    public static final int GOOD_COMMENT = 1;
    public static final int CENTER_COMMENT = 2;
    public static final int BAD_COMMENT = 3;
    public static final int HASIMAGE_COMMENT = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_comment,container,false);
        pId = getArguments().getLong("pid");
        comment_lv = (FlexiListView) mView.findViewById(R.id.comment_lv);
        mAll = (LinearLayout) mView.findViewById(R.id.all_comment_ll);
        mGood = (LinearLayout) mView.findViewById(R.id.positive_comment_ll);
        mCenter = (LinearLayout) mView.findViewById(R.id.center_comment_ll);
        mBad = (LinearLayout) mView.findViewById(R.id.nagetive_comment_ll);
        mHasImg = (LinearLayout) mView.findViewById(R.id.has_image_comment_ll);
        mAll.setOnClickListener(this);
        mGood.setOnClickListener(this);
        mCenter.setOnClickListener(this);
        mBad.setOnClickListener(this);
        mHasImg.setOnClickListener(this);

        all_comment_tip = (TextView) mView.findViewById(R.id.all_comment_tip);
        positive_comment_tip = (TextView) mView.findViewById(R.id.positive_comment_tip);
        center_comment_tip = (TextView) mView.findViewById(R.id.center_comment_tip);
        nagetive_comment_tip = (TextView) mView.findViewById(R.id.nagetive_comment_tip);
        has_image_comment_tip = (TextView) mView.findViewById(R.id.has_image_comment_tip);

        all_comment_tv = (TextView) mView.findViewById(R.id.all_comment_tv);
        positive_comment_tv = (TextView) mView.findViewById(R.id.positive_comment_tv);
        center_comment_tv = (TextView) mView.findViewById(R.id.center_comment_tv);
        nagetive_comment_tv = (TextView) mView.findViewById(R.id.nagetive_comment_tv);
        has_image_comment_tv = (TextView) mView.findViewById(R.id.has_image_comment_tv);

        all_comment_tip.setSelected(true);
        all_comment_tv.setSelected(true);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mController = new ProductController(getActivity());
        mController.setModeChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_COUNT_ACTION, pId);
        mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, ALL_COMMENT);
        mAdapter = new CommentAdapter(getContext());
        comment_lv.setAdapter(mAdapter);
    }

    private Handler mHadler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.GET_COMMENT_COUNT_ACTION:
                    handleCommentCount((CommentCountBean) msg.obj);
                    break;
                case IdiyMessage.GET_COMMENT_DETAIL:
                    handleCommentDetail((List<CommentDetailBean>) msg.obj);
                    break;

            }
        }
    };

    private void handleCommentDetail(List<CommentDetailBean> data) {
        if(data != null && data.size() > 0) {
            Toast.makeText(getActivity(), data.size()+"", Toast.LENGTH_SHORT).show();
            mAdapter.setDatas(data);
        }
    }

    private void handleCommentCount(CommentCountBean data) {
        if(data != null) {
            all_comment_tv.setText(data.getAllComment());
            positive_comment_tv.setText(data.getPositiveCom());
            center_comment_tv.setText(data.getModerateCom());
            nagetive_comment_tv.setText(data.getNegativeCom());
            has_image_comment_tv.setText(data.getHasImgCom());
        }
    }

    @Override
    public void onClick(View view) {
        resetAllColor();
        switch (view.getId()) {
            case R.id.all_comment_ll:
                all_comment_tip.setSelected(true);
                all_comment_tv.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, ALL_COMMENT);
                break;
            case R.id.positive_comment_ll:
                positive_comment_tip.setSelected(true);
                positive_comment_tv.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, GOOD_COMMENT);
                break;
            case R.id.center_comment_ll:
                center_comment_tv.setSelected(true);
                center_comment_tip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, CENTER_COMMENT);
                break;
            case R.id.nagetive_comment_ll:
                nagetive_comment_tip.setSelected(true);
                nagetive_comment_tv.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, BAD_COMMENT);
                break;
            case R.id.has_image_comment_ll:
                has_image_comment_tip.setSelected(true);
                has_image_comment_tv.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_DETAIL, pId, HASIMAGE_COMMENT);
                break;
        }
    }

    private void resetAllColor() {
        all_comment_tip.setSelected(false);
        all_comment_tv.setSelected(false);
        positive_comment_tip.setSelected(false);
        positive_comment_tv.setSelected(false);
        center_comment_tv.setSelected(false);
        center_comment_tip.setSelected(false);
        nagetive_comment_tip.setSelected(false);
        nagetive_comment_tv.setSelected(false);
        has_image_comment_tip.setSelected(false);
        has_image_comment_tv.setSelected(false);
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHadler.obtainMessage(action, data[0]).sendToTarget();
    }


}
