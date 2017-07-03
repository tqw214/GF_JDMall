package com.viger.gfJdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.BrandAdapter;
import com.viger.gfJdmall.adapter.ProductListAdapter;
import com.viger.gfJdmall.bean.BrandBean;
import com.viger.gfJdmall.bean.ProductListBean;
import com.viger.gfJdmall.bean.ProductSortBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.CategoryController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.listener.IProductSortChangeListener;
import com.viger.gfJdmall.ui.FlexiListView;
import com.viger.gfJdmall.ui.FlexiScrollView;
import com.viger.gfJdmall.ui.pop.ProductSortPopWindow;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ProductListActivity extends BaseActivity implements IModeChangeListener,IProductSortChangeListener {

    private DrawerLayout drawerlayout;
    private int mLevel1Id;
    private int mLevel2Id;
    private GridView brand_gv;
    private BrandAdapter mAdapter;
    private List<BrandBean> mBrandList;
    private TextView all_indicator;
    private TextView sale_indicator;
    private TextView price_indicator;
    private ProductSortPopWindow mProductSortPopWindow;
    private ProductSortBean mPsb;
    private TextView jd_take_tv,paywhenreceive_tv,justhasstock_tv;
    private TextView choose_indicator;
    private FlexiScrollView mSliderView;
    private EditText minPrice_et, maxPrice_et;
    private List<ProductListBean> mProductListDatas;
    private FlexiListView mListView;
    private ProductListAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initData();
        initController();
        initView();
        mController.sendAsyncMessage(IdiyMessage.BRAND_ACTION, mLevel1Id);
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
    }

    @Override
    protected void initController() {
        mController = new CategoryController(this);
        mController.setModeChangeListener(this);
    }

    public void fuwu(View v) {
        jd_take_tv.setSelected(v.getId() == R.id.jd_take_tv);
        paywhenreceive_tv.setSelected(v.getId() == R.id.paywhenreceive_tv);
        justhasstock_tv.setSelected(v.getId() == R.id.justhasstock_tv);
    }


    public void goback(View v) {
        finish();
    }

    private void initData() {
        Intent intent = getIntent();
        mLevel1Id = intent.getIntExtra("id1", 0);
        mLevel2Id = intent.getIntExtra("id2", 0);
        mPsb = new ProductSortBean();
        mPsb.setCategoryId(mLevel2Id);
    }

    @Override
    protected void initView() {
        mListView = (FlexiListView) findViewById(R.id.product_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductListBean item = (ProductListBean) mProductAdapter.getItem(i);
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("pid", item.getId());
                startActivity(intent);
            }
        });
        mProductAdapter = new ProductListAdapter(this);
        mListView.setAdapter(mProductAdapter);

        minPrice_et = (EditText) findViewById(R.id.minPrice_et);
        maxPrice_et = (EditText) findViewById(R.id.maxPrice_et);
        mSliderView = (FlexiScrollView) findViewById(R.id.slide_view);
        jd_take_tv = (TextView) findViewById(R.id.jd_take_tv);
        paywhenreceive_tv = (TextView) findViewById(R.id.paywhenreceive_tv);
        justhasstock_tv = (TextView) findViewById(R.id.justhasstock_tv);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        brand_gv = (GridView) findViewById(R.id.brand_gv);
        all_indicator = (TextView) findViewById(R.id.all_indicator);
        all_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                if(mProductSortPopWindow == null) {
                    mProductSortPopWindow = new ProductSortPopWindow(ProductListActivity.this);
                    mProductSortPopWindow.setProductSortChangeListener(ProductListActivity.this);
                }
                mProductSortPopWindow.onShow(all_indicator);
            }
        });
        sale_indicator = (TextView) findViewById(R.id.sale_indicator);
        sale_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPsb.setSortType(1);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
            }
        });
        price_indicator = (TextView) findViewById(R.id.price_indicator);
        price_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //排序条件（0, 1-销量 2-价格高到低 3-价格低到高）
                int sortType = mPsb.getSortType();
                if(sortType == 0 || sortType == 1 || sortType == 3) {
                    mPsb.setSortType(2);
                }
                if(sortType == 0 || sortType == 1 || sortType == 2) {
                    mPsb.setSortType(3);
                }
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
            }
        });
        choose_indicator = (TextView) findViewById(R.id.choose_indicator);
        choose_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOpen = drawerlayout.isDrawerOpen(mSliderView);
                if(!isOpen) {
                    drawerlayout.openDrawer(mSliderView);
                }
            }
        });
        mAdapter = new BrandAdapter(this);
        brand_gv.setAdapter(mAdapter);
        brand_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.setCurrentIndex(i);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void chooseSearchClick(View view) {
        //配送方式
        int count = 0;
        if(jd_take_tv.isSelected()) {
            count += 1;
        }
        if(paywhenreceive_tv.isSelected()) {
            count += 2;
        }
        if(justhasstock_tv.isSelected()) {
            count += 4;
        }
        mPsb.setDeliverChoose(count);
        //价格区间
        String minPrice = minPrice_et.getText().toString();
        String maxPrice = maxPrice_et.getText().toString();
        if(!TextUtils.isEmpty(minPrice) && !TextUtils.isEmpty(maxPrice)) {
            mPsb.setMinPrice(Integer.valueOf(minPrice));
            mPsb.setMaxPrice(Integer.valueOf(maxPrice));
        }
        //品牌
        int index = mAdapter.getCurrentIndex();
        if(index != -1) {
            mPsb.setBrandId(mBrandList.get(index).getId());
        }
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
        drawerlayout.closeDrawer(mSliderView);
    }

    public void resetClick(View view) {
        mPsb = new ProductSortBean();
        mPsb.setCategoryId(mLevel2Id);
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
        drawerlayout.closeDrawer(mSliderView);
    }

    @Override
    public void onModeChange(int action, Object... data) {
       mHandler.obtainMessage(action, data[0]).sendToTarget();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.BRAND_ACTION:
                    mBrandList = (List<BrandBean>) msg.obj;
                    Log.i("tag", mBrandList.size() + "个");
                    handleBrandData();
                    break;
                case IdiyMessage.PRODUCT_LIST_ACTION:
                    mProductListDatas = (List<ProductListBean>) msg.obj;
                    handleProductListData();
                    break;
            }
        }
    };

    private void handleProductListData() {
        if(mProductListDatas != null && mProductListDatas.size() > 0) {
            Log.i("tag", mProductListDatas.size()+"");
            mProductAdapter.setDatas(mProductListDatas);
        }
    }

    private void handleBrandData() {
        if(mBrandList != null && mBrandList.size() > 0) {
            mAdapter.setDatas(mBrandList);
        }
    }

    @Override
    public void onSortChanged(int action) {
        Log.i("tag", action + "");
        switch (action) {
            case IProductSortChangeListener.ALLSORT:
                all_indicator.setText("综合");
                mPsb.setFilterType(1);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
                break;
            case IProductSortChangeListener.NEWSSORT:
                all_indicator.setText("新品");
                mPsb.setFilterType(2);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
                break;
            case IProductSortChangeListener.COMMENTSORT:
                all_indicator.setText("评价");
                mPsb.setFilterType(3);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mPsb);
                break;
        }
    }
}
