package com.viger.gfJdmall.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ProductController;
import com.viger.gfJdmall.fragment.ProductCommentFragment;
import com.viger.gfJdmall.fragment.ProductDetailsFragment;
import com.viger.gfJdmall.fragment.ProductIntroduceFragment;
import com.viger.gfJdmall.listener.IModeChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ProductDetailActivity extends BaseActivity implements IModeChangeListener,View.OnClickListener{

    private long pId;
    private LinearLayout details_ll;  //详情
    private LinearLayout introduce_ll;  //商品
    private LinearLayout comment_ll;  //评论
    private ImageView more_iv;
    private ViewPager container_vp;
    private TextView add2shopcar;
    private View details_view,introduce_view,comment_view;
    private List<Fragment> mFragments;
    private ProductDetailsFragment mProductDetailsFragment;
    private ProductCommentFragment mProductCommentFragment;
    public int mCount= 1;
    private String mProVersion="";

    public void setmCount(int count) {
        this.mCount = count;
    }

    public void setmProVersion(String proVersion) {
        this.mProVersion = proVersion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initData();
        initView();
        initController();
    }

    @Override
    protected void initController() {
        mController = new ProductController(this);
        mController.setModeChangeListener(this);
    }

    public long getPid() {
        return this.pId;
    }

    private void initData() {
        Intent intent = getIntent();
        pId = intent.getLongExtra("pid", 0);
    }

    @Override
    protected void initView() {
        details_ll = (LinearLayout) findViewById(R.id.details_ll);
        details_ll.setOnClickListener(this);
        introduce_ll = (LinearLayout) findViewById(R.id.introduce_ll);
        introduce_ll.setOnClickListener(this);
        comment_ll = (LinearLayout) findViewById(R.id.comment_ll);
        comment_ll.setOnClickListener(this);
        more_iv = (ImageView) findViewById(R.id.more_iv);
        container_vp = (ViewPager) findViewById(R.id.container_vp);
        add2shopcar = (TextView) findViewById(R.id.add2shopcar);

        details_view = findViewById(R.id.details_view);
        introduce_view = findViewById(R.id.introduce_view);
        comment_view = findViewById(R.id.comment_view);

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new ProductIntroduceFragment());
        mProductDetailsFragment = new ProductDetailsFragment();
        Bundle b = new Bundle();
        b.putLong("pid", pId);
        mProductDetailsFragment.setArguments(b);

        mProductCommentFragment = new ProductCommentFragment();
        mProductCommentFragment.setArguments(b);

        mFragments.add(mProductDetailsFragment);
        mFragments.add(mProductCommentFragment);
        container_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        container_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hiddleAllView();
                switch (position) {
                    case 0:
                        introduce_view.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        details_view.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        comment_view.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void goBack(View view) {
        finish();
    }

    /**
     * 加入购物车
     * pId,userId,count
     * @param view
     */
    public void add2ShopCar(View view) {
        MyApplication application = (MyApplication) this.getApplication();
        long userId = application.getUserInfo().getId();

        //Toast.makeText(this, "pId=" + pId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "userId=" + userId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "count=" + mCount, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "proVersiont=" + mProVersion, Toast.LENGTH_SHORT).show();

        if(mCount == 0) {
            Toast.makeText(this, "购买数量必须大于0", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mProVersion.equals("")) {
            Toast.makeText(this, "请选择商品类型", Toast.LENGTH_SHORT).show();
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.TO_SHOP_CAR, userId, pId, mCount, mProVersion);

    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandler.obtainMessage(action, data[0]).sendToTarget();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IdiyMessage.TO_SHOP_CAR:
                    handleToShopCar((RResult) msg.obj);
                    break;
            }
        }
    };

    private void handleToShopCar(RResult result) {
        if(result != null) {
            if(result.isSuccess()) {
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "添加失败 " + result.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_ll:
                hiddleAllView();
                details_view.setVisibility(View.VISIBLE);
                container_vp.setCurrentItem(1,false);
                break;
            case R.id.introduce_ll:
                hiddleAllView();
                introduce_view.setVisibility(View.VISIBLE);
                container_vp.setCurrentItem(0,false);
                break;
            case R.id.comment_ll:
                hiddleAllView();
                comment_view.setVisibility(View.VISIBLE);
                container_vp.setCurrentItem(2,false);
                break;
        }
    }

    private void hiddleAllView() {
        details_view.setVisibility(View.INVISIBLE);
        introduce_view.setVisibility(View.INVISIBLE);
        comment_view.setVisibility(View.INVISIBLE);

    }


}
