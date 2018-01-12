package com.viger.gfJdmall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.fragment.BaseFragment;
import com.viger.gfJdmall.fragment.CategoryFragment;
import com.viger.gfJdmall.fragment.HomeFragment;
import com.viger.gfJdmall.fragment.MyJDFragment;
import com.viger.gfJdmall.fragment.ShopcarFragment;
import com.viger.gfJdmall.listener.IBottomBarClickListener;
import com.viger.gfJdmall.ui.BottomBar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IBottomBarClickListener {

    private BottomBar mBottomBar;
    private List<BaseFragment> mFragments;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private ShopcarFragment shopcarFragment;
    private MyJDFragment myJDFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initController();

    }

    @Override
    protected void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        mBottomBar.setIBottomBarClickListener(this);
        mFragments = new ArrayList<BaseFragment>();
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        shopcarFragment = new ShopcarFragment();
        myJDFragment = new MyJDFragment();
        mFragments.add(homeFragment);
        mFragments.add(categoryFragment);
        mFragments.add(shopcarFragment);
        mFragments.add(myJDFragment);

        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        for (BaseFragment fragment : mFragments) {
            mTransaction.add(R.id.top_bar, fragment);
            mTransaction.hide(fragment);
        }
        mTransaction.show(mFragments.get(0));
        mTransaction.commit();
    }

    @Override
    protected void initController() {

    }

    @Override
    public void onItemClick(int action) {
        mTransaction = mFragmentManager.beginTransaction();
        for (BaseFragment fragment : mFragments) {
            mTransaction.hide(fragment);
        }
        switch (action) {
            case R.id.frag_main_ll:
                mTransaction.show(homeFragment);
                break;
            case R.id.frag_category_ll:
                mTransaction.show(categoryFragment);
                break;
            case R.id.frag_shopcar_ll:
                mTransaction.show(shopcarFragment);
                shopcarFragment.update();
                break;
            case R.id.frag_mine_ll:
                mTransaction.show(myJDFragment);
                break;
        }
        mTransaction.commit();
    }


}
