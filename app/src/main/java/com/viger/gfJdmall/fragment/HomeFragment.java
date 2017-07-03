package com.viger.gfJdmall.fragment;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.activity.ProductDetailActivity;
import com.viger.gfJdmall.activity.ProductListActivity;
import com.viger.gfJdmall.adapter.RecommendAdapter;
import com.viger.gfJdmall.adapter.SecondKillAdapter;
import com.viger.gfJdmall.bean.Banner;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.RecommendBean;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.controller.HomeController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.HorizontalListView;
import com.viger.gfJdmall.utils.NetworkUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment implements IModeChangeListener {

	private View mView;
	private ImageView scan_iv;
	private EditText search_et;
	private ImageView message_iv;
	private ViewPager ad_vp;
	private LinearLayout ad_indicator;
	private TextView seckill_tv;  //秒杀倒计时
	private HorizontalListView horizon_listview;
	private GridView recommend_gv;
	private ADAdapter mADAdapter;
	private List<Banner> mBanners;
	private List<SmartImageView> mImageViews;
	private Timer mTimer;
	private BannerTimerTask mTimerTask;
	private SecondKillAdapter secondKillAdapter;
	private SecondKillBean mSecondKillBean;
	private RecommendAdapter mRecommendAdapter;
	private List<RecommendBean> mRecommendBeans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home, container, false);
		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		scan_iv = (ImageView) mView.findViewById(R.id.scan_iv);
		search_et = (EditText) mView.findViewById(R.id.search_et);
		message_iv = (ImageView) mView.findViewById(R.id.message_iv);
		ad_vp = (ViewPager) mView.findViewById(R.id.ad_vp);
		ad_indicator = (LinearLayout) mView.findViewById(R.id.ad_indicator);
		seckill_tv = (TextView) mView.findViewById(R.id.seckill_tv);
		horizon_listview = (HorizontalListView) mView.findViewById(R.id.horizon_listview);
		secondKillAdapter = new SecondKillAdapter(getActivity());
		horizon_listview.setAdapter(secondKillAdapter);
		horizon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				List<SecondKillBean.RowsBean> rows = mSecondKillBean.getRows();
				SecondKillBean.RowsBean rowsBean = rows.get(i);
				Toast.makeText(getActivity(), rowsBean.getProductId(), Toast.LENGTH_SHORT).show();
				startDetailActivity(Long.valueOf(rowsBean.getProductId()));
			}
		});

		//猜你喜欢模块
		recommend_gv = (GridView) mView.findViewById(R.id.recommend_gv);
		mRecommendAdapter = new RecommendAdapter(getActivity());
		recommend_gv.setAdapter(mRecommendAdapter);
		recommend_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				RecommendBean item = (RecommendBean) adapterView.getItemAtPosition(i);
				Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
				startDetailActivity(item.getProductId());
			}
		});

		initController();
		mImageViews = new ArrayList<SmartImageView>();
	}

	private void startDetailActivity(long pId) {
		Intent intent = new Intent(getContext(), ProductDetailActivity.class);
		intent.putExtra("pid", pId);
		startActivity(intent);
	}

	@Override
	protected void initController() {
		mController = new HomeController(getActivity());
		mController.setModeChangeListener(this);
		mController.sendAsyncMessage(IdiyMessage.GET_BANNER_ACTION, 1);
		mController.sendAsyncMessage(IdiyMessage.SECOND_KILL_ACTION);
		//猜你喜欢模块数据请求
		mController.sendAsyncMessage(IdiyMessage.RECOMMEND_ACTION);
	}

	@Override
	protected void myHandleMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.GET_BANNER_ACTION:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					mBanners = (List<Banner>) o[0];
					initBannerData();
				}
				break;
			case IdiyMessage.SECOND_KILL_ACTION:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					mSecondKillBean = (SecondKillBean) o[0];
					handleSecondKill();
				}
				break;
			case IdiyMessage.RECOMMEND_ACTION:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					mRecommendBeans = (List<RecommendBean>) o[0];
					handleRecommend();
				}
				break;
		}
	}

	private void handleRecommend() {
		if(mRecommendBeans != null) {
			mRecommendAdapter.setDatas(mRecommendBeans);
		}
	}

	private void handleSecondKill() {
		if(mSecondKillBean != null) {
			String total = mSecondKillBean.getTotal();
			List<SecondKillBean.RowsBean> rows = mSecondKillBean.getRows();
			secondKillAdapter.setDatas(rows);
		}
	}

	private void initBannerData() {
		if(mBanners != null && mBanners.size() > 0) {
			for (int i=0;i<mBanners.size();i++) {
				SmartImageView view = new SmartImageView(getActivity());
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				view.setImageResource(R.drawable.alipay_btn_bg);
				view.setScaleType(ImageView.ScaleType.FIT_XY);
				view.setImageUrl(NetworkConst.BASE_URL + mBanners.get(i).getAdUrl());
				view.setLayoutParams(params);
				mImageViews.add(view);

				//添加小圆点
				ImageView in = new ImageView(getActivity());
				LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(20, 20);
				in.setImageResource(R.drawable.ad_indicator_bg);
				par.leftMargin = 10;
				in.setEnabled(i == 0 ? true : false);
				in.setLayoutParams(par);
				ad_indicator.addView(in);
			}
		}

		mADAdapter = new ADAdapter();
		ad_vp.setAdapter(mADAdapter);
		ad_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				//position = position % mImageViews.size();
				int childCount = ad_indicator.getChildCount();
				for(int i=0;i<childCount;i++) {
					View view = ad_indicator.getChildAt(i);
					view.setEnabled(i == position ? true : false);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		ad_vp.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
						if(mTimerTask != null) {
							mTimerTask.cancel();
						}
						break;
					case MotionEvent.ACTION_UP:
						mTimerTask = new BannerTimerTask();
						mTimer.schedule(mTimerTask, 3000 , 3000);
						break;
				}
				return false;
			}
		});

		//ad_vp.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2 % mImageViews.size());

		//开启自动播放
		mTimer = new Timer();
		mTimerTask = new BannerTimerTask();
		mTimer.schedule(mTimerTask, 3000 , 3000);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mTimer != null) {
			mTimer.cancel();
		}
	}

	private class BannerTimerTask extends TimerTask {
		@Override
		public void run() {
			ad_vp.post(new Runnable() {
				@Override
				public void run() {
					int currentItem = ad_vp.getCurrentItem();
					currentItem++;
					if(currentItem >= mBanners.size()) {
						currentItem = 0;
					}
					ad_vp.setCurrentItem(currentItem);
				}
			});
		}
	}

	private class ADAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.size();
			//return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//SmartImageView view = mImageViews.get(position%mImageViews.size());
			SmartImageView view = mImageViews.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((SmartImageView)object);
		}
	}


}
