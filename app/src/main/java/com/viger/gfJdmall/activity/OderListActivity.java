package com.viger.gfJdmall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.fragment.AllOrderFragment;
import com.viger.gfJdmall.fragment.BaseFragment;
import com.viger.gfJdmall.fragment.CompletedOrderFragment;
import com.viger.gfJdmall.fragment.WaitPayFragment;
import com.viger.gfJdmall.fragment.WaitReceiveFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class OderListActivity extends BaseActivity {

    private LinearLayout all_order_ll,wait_pay_ll,wait_receive_ll,complete_order_ll;
    private View all_order_view,wait_pay_view,wait_receive_view,complete_order_view;
    private ViewPager order_vp;
    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    @Override
    protected void initView() {
        all_order_ll = (LinearLayout) findViewById(R.id.all_order_ll);
        wait_pay_ll = (LinearLayout) findViewById(R.id.wait_pay_ll);
        wait_receive_ll = (LinearLayout) findViewById(R.id.wait_receive_ll);
        complete_order_ll = (LinearLayout) findViewById(R.id.complete_order_ll);
        all_order_view = findViewById(R.id.all_order_view);
        wait_pay_view = findViewById(R.id.wait_pay_view);
        wait_receive_view = findViewById(R.id.wait_receive_view);
        complete_order_view = findViewById(R.id.complete_order_view);
        order_vp = (ViewPager) findViewById(R.id.order_vp);
        mAdapter = new OrderAdapter(getSupportFragmentManager());
        order_vp.setAdapter(mAdapter);
        order_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                all_order_view.setVisibility(position == 0 ?
                        View.VISIBLE : View.INVISIBLE);
                wait_pay_view.setVisibility(position == 1 ?
                        View.VISIBLE : View.INVISIBLE);
                wait_receive_view.setVisibility(position == 2 ?
                        View.VISIBLE : View.INVISIBLE);
                complete_order_view.setVisibility(position == 3 ?
                        View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class OrderAdapter extends FragmentPagerAdapter {

        private List<BaseFragment> mItems;

        public OrderAdapter(FragmentManager fm) {
            super(fm);
            mItems = new ArrayList<BaseFragment>();
            mItems.add(new AllOrderFragment());
            mItems.add(new WaitPayFragment());
            mItems.add(new WaitReceiveFragment());
            mItems.add(new CompletedOrderFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }
    }

    public void tabClick(View view) {
        all_order_view.setVisibility(view.getId() == R.id.all_order_ll ?
                View.VISIBLE : View.INVISIBLE);
        wait_pay_view.setVisibility(view.getId() == R.id.wait_pay_ll ?
                View.VISIBLE : View.INVISIBLE);
        wait_receive_view.setVisibility(view.getId() == R.id.wait_receive_ll ?
                View.VISIBLE : View.INVISIBLE);
        complete_order_view.setVisibility(view.getId() == R.id.complete_order_ll ?
                View.VISIBLE : View.INVISIBLE);
        switch (view.getId()) {
            case R.id.all_order_ll:
                order_vp.setCurrentItem(0, false);
                break;
            case R.id.wait_pay_ll:
                order_vp.setCurrentItem(1, false);
                break;
            case R.id.wait_receive_ll:
                order_vp.setCurrentItem(2, false);
                break;
            case R.id.complete_order_ll:
                order_vp.setCurrentItem(3, false);
                break;
        }
    }

    public void goBack(View v) {
        finish();
    }


}
