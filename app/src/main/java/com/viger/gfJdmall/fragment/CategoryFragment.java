package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.TopCategoryAdapter;
import com.viger.gfJdmall.bean.CategoryBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.CategoryController;
import com.viger.gfJdmall.ui.SubCategoryView;

import java.util.List;

public class CategoryFragment extends BaseFragment {

	private View mView;
	private ListView top_lv;
	private TopCategoryAdapter mCategoryAdapter;
	private List<CategoryBean> mCategoryBeans;
	private SubCategoryView mCategoryView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_category, container, false);
		top_lv = (ListView) mView.findViewById(R.id.top_lv);
		mCategoryView = (SubCategoryView) mView.findViewById(R.id.subcategory);
		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initData();
	}

	private void initData() {
		mCategoryAdapter = new TopCategoryAdapter(getActivity());
		top_lv.setAdapter(mCategoryAdapter);
		top_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mCategoryAdapter.setmCurrentPosition(i);
				CategoryBean item = (CategoryBean) mCategoryAdapter.getItem(i);
				mCategoryView.onShow(item);
			}
		});
	}

	@Override
	protected void initController() {
		super.initController();
		mController = new CategoryController(getActivity());
		mController.setModeChangeListener(this);
		mController.sendAsyncMessage(IdiyMessage.CATEGORY_ACTION);
	}

	@Override
	protected void myHandleMessage(Message msg) {
		switch (msg.what)  {
			case IdiyMessage.CATEGORY_ACTION:
				Object[] obj = (Object[]) msg.obj;
				mCategoryBeans = (List<CategoryBean>) obj[0];
				handleCategoryInfo();
				break;
		}

	}

	private void handleCategoryInfo() {
		if(mCategoryBeans != null) {
			mCategoryAdapter.setDatas(mCategoryBeans);
			top_lv.performItemClick(null, 0, 0);
		}
	}




}
