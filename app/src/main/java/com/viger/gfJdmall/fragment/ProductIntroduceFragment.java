package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.activity.ProductDetailActivity;
import com.viger.gfJdmall.adapter.GoodCommentAdapter;
import com.viger.gfJdmall.adapter.ProductAdAdapter;
import com.viger.gfJdmall.adapter.TypeListAdapter;
import com.viger.gfJdmall.bean.GoodCommentBean;
import com.viger.gfJdmall.bean.ProductInfoBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ProductController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.listener.INumberInputListener;
import com.viger.gfJdmall.ui.MyListView;
import com.viger.gfJdmall.ui.NumberInputView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lean on 16/10/28.
 */

public class ProductIntroduceFragment extends Fragment implements IModeChangeListener{

    private View mView;
    private ViewPager advp;
    private TextView vp_indic_tv;
    private TextView name_tv;
    private TextView self_sale_tv;
    private TextView recommend_p_tv;
    private TextView recommend_buy_tv;
    private TextView price_tv;
    private TextView tip_tv;
    private ListView product_versions_lv;
    private MyListView good_comment_lv;
    private NumberInputView number_input_et;
    private TextView good_rate_tv;
    private TextView good_comment_tv;

    private ProductAdAdapter mAdAdapter;
    private ProductController mController;
    private Timer mTimer;
    private int currentItem;

    private TypeListAdapter mTypeAdapter;
    private GoodCommentAdapter mGoodCommentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_introduce,container,false);
        advp = (ViewPager) mView.findViewById(R.id.advp);
        vp_indic_tv = (TextView) mView.findViewById(R.id.vp_indic_tv);
        name_tv = (TextView) mView.findViewById(R.id.name_tv);
        self_sale_tv = (TextView) mView.findViewById(R.id.self_sale_tv);
        recommend_p_tv = (TextView) mView.findViewById(R.id.recommend_p_tv);
        recommend_buy_tv = (TextView) mView.findViewById(R.id.recommend_buy_tv);
        price_tv = (TextView) mView.findViewById(R.id.price_tv);
        tip_tv = (TextView) mView.findViewById(R.id.tip_tv);
        product_versions_lv = (ListView) mView.findViewById(R.id.product_versions_lv);
        mTypeAdapter = new TypeListAdapter(getContext());
        product_versions_lv.setAdapter(mTypeAdapter);
        product_versions_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTypeAdapter.setCurrentIndex(i);
                ((ProductDetailActivity)getActivity()).setmProVersion(mTypeAdapter.getItem(i).toString());
            }
        });

        good_comment_lv = (MyListView) mView.findViewById(R.id.good_comment_lv);
        number_input_et = (NumberInputView) mView.findViewById(R.id.number_input_et);
        good_rate_tv = (TextView) mView.findViewById(R.id.good_rate_tv);
        good_comment_tv = (TextView) mView.findViewById(R.id.good_comment_tv);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mController = new ProductController(getActivity());
        mController.setModeChangeListener(this);
        recommend_buy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击购买
            }
        });
        mAdAdapter = new ProductAdAdapter(getContext());
        advp.setAdapter(mAdAdapter);

        mGoodCommentAdapter = new GoodCommentAdapter(getContext());
        good_comment_lv.setAdapter(mGoodCommentAdapter);

        mController.sendAsyncMessage(IdiyMessage.PRODUCT_INFO_ACTION, ((ProductDetailActivity)getActivity()).getPid());
        mController.sendAsyncMessage(IdiyMessage.GOOD_COMMENT_ACTION_RESULT, ((ProductDetailActivity)getActivity()).getPid());
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHadler.obtainMessage(action, data[0]).sendToTarget();
    }

    private Handler mHadler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.PRODUCT_INFO_ACTION:
                    ProductInfoBean bean = (ProductInfoBean) msg.obj;
                    handleProductInfo(bean);
                    break;
                case IdiyMessage.GOOD_COMMENT_ACTION_RESULT:
                    handleGoodComment((List<GoodCommentBean>) msg.obj);
                    break;
            }
        }
    };

    private void handleGoodComment(List<GoodCommentBean> data) {
        mGoodCommentAdapter.setDatas(data);
    }

    private void handleProductInfo(ProductInfoBean info) {
        if(info != null) {
            final List<String> data = JSON.parseArray(info.getImgUrls(), String.class);
            mAdAdapter.setData(data);
            vp_indic_tv.setText("1/"+data.size());

            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(data.size() > 0) {
                        currentItem = advp.getCurrentItem();
                        currentItem++;
                        if(currentItem > data.size() -1) {
                            currentItem = 0;
                        }
                        mHadler.post(new Runnable() {
                            @Override
                            public void run() {
                                advp.setCurrentItem(currentItem);
                                vp_indic_tv.setText((currentItem+1)+"/"+data.size());
                            }
                        });
                    }
                }
            }, 3000, 3000);

            name_tv.setText(info.getName());
            self_sale_tv.setVisibility(info.getIfSaleOneself() ? View.VISIBLE : View.INVISIBLE);
            recommend_p_tv.setText(info.getRecomProduct());

            handleTypeList(info.getTypeList());
            number_input_et.setMax(Integer.valueOf(info.getStockCount()));
            number_input_et.setListener(new INumberInputListener() {
                @Override
                public void onTextChange(int num) {
                    ((ProductDetailActivity)getActivity()).setmCount(num);
                }
            });

            good_rate_tv.setText(info.getFavcomRate()+"%好评");
            good_comment_tv.setText(info.getCommentCount()+"人评价");
        }
    }

    private void handleTypeList(String typeJson) {
        List<String> typeList = JSON.parseArray(typeJson, String.class);
        mTypeAdapter.setDatas(typeList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}

