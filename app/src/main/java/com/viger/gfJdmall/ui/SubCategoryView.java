package com.viger.gfJdmall.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.activity.ProductListActivity;
import com.viger.gfJdmall.bean.CategoryBean;
import com.viger.gfJdmall.bean.SubCategoryBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.controller.BaseController;
import com.viger.gfJdmall.controller.CategoryController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.listener.IViewContainer;

import java.util.List;

public class SubCategoryView extends FlexiScrollView implements IViewContainer, IModeChangeListener {

	private CategoryBean item;
	private LinearLayout mContainer;
	private Context mContext;
	private BaseController mController;
	private List<SubCategoryBean> mSubCategoryBeans;
	private int lineNum = 3; //每行几个商品

	public SubCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initController();
		mContainer = (LinearLayout) findViewById(R.id.child_container_ll);
	}

	private void initController() {
		mController = new CategoryController(getContext());
		mController.setModeChangeListener(this);
	}

	@Override
	public void onShow(Object... values) {
		mContainer.removeAllViews();
		item = (CategoryBean) values[0];
		initBanner();
		mController.sendAsyncMessage(IdiyMessage.SUB_CATEGORY_ACTION, item.getId());
	}

	private void initBanner() {
		String bannerUrl = NetworkConst.BASE_URL + item.getBannerUrl();
		SmartImageView siv = new SmartImageView(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		//params.setMargins(20,20,20,20);
		siv.setLayoutParams(params);
		siv.setScaleType(ImageView.ScaleType.FIT_XY);
		siv.setImageUrl(bannerUrl);
		mContainer.addView(siv);
	}

	@Override
	public void onModeChange(int action, Object... data) {
		mHandler.obtainMessage(action, data[0]).sendToTarget();
	}

	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IdiyMessage.SUB_CATEGORY_ACTION:
					mSubCategoryBeans = (List<SubCategoryBean>) msg.obj;
					handleSubCategory();
					break;
			}
		}
	};

	//处理二级三级菜单
	private void handleSubCategory() {
		if(mSubCategoryBeans != null) {

			for (SubCategoryBean bean : mSubCategoryBeans) {
				//添加一条分割线
				View lineView = new View(getContext());
				LinearLayout.LayoutParams p_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
						1);
				p_line.topMargin = 10;
				lineView.setBackgroundColor(Color.GRAY);
				lineView.setLayoutParams(p_line);
				mContainer.addView(lineView);

				LinearLayout ll = new LinearLayout(getContext());
				LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				//p1.setMargins(5,10,5,10);
				ll.setLayoutParams(p1);
				TextView tv = new TextView(getContext());
				tv.setText(bean.getName());
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				ll.addView(tv);
				mContainer.addView(ll);

				List<SubCategoryBean.ThirdCategoryBean> thirdCategory = bean.getThirdCategory();
				//计算行数
				int total = thirdCategory.size();
				int lines = thirdCategory.size() / lineNum;
				int rema = total % lineNum;
				lines += (rema==0?0:1);

				for(int i=0;i<lines;i++) {
					LinearLayout ll2 = new LinearLayout(getContext());
					LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					ll2.setLayoutParams(p2);
					ll2.setOrientation(LinearLayout.HORIZONTAL);

					//获取当前行有几个元素
					int mx =  total - i * lineNum;
					mx = mx > lineNum ? lineNum : mx;

					for (int j=0;j<mx;j++) {
						LinearLayout ll3 = new LinearLayout(getContext());
						ll3.setOrientation(LinearLayout.VERTICAL);
						//LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(getWidth()/3,
						//		LinearLayout.LayoutParams.WRAP_CONTENT);
						LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);
						p3.leftMargin = 20;
						ll3.setLayoutParams(p3);
						SmartImageView siv = new SmartImageView(getContext());
						TextView tv2 = new TextView(getContext());
						int index = j + i * lineNum;
						final SubCategoryBean.ThirdCategoryBean bean2 = thirdCategory.get(index);
						//siv.setScaleType(ImageView.ScaleType.FIT_XY);
						siv.setImageUrl(NetworkConst.BASE_URL + bean2.getBannerUrl());
						tv2.setText(bean2.getName());
						tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
						ll3.addView(siv);
						ll3.addView(tv2);
						ll2.addView(ll3);
						ll3.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent intent = new Intent(getContext(), ProductListActivity.class);
								intent.putExtra("id2", bean2.getId());
								intent.putExtra("id1", item.getId());
								getContext().startActivity(intent);
							}
						});
					}
					mContainer.addView(ll2);
				}



			}

		}
	}

}
