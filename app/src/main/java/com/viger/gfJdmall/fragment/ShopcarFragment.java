package com.viger.gfJdmall.fragment;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.activity.SettleActivity;
import com.viger.gfJdmall.adapter.ShopCarAdapter;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.ShopCarBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ProductController;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.ui.FlexiListView;
import com.viger.gfJdmall.ui.LoadingDialog;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShopcarFragment extends BaseFragment {

	private View mView;
	private FlexiListView shopcar_lv;
	private CheckBox all_cbx;
	private TextView all_money_tv;
	private TextView settle_tv;
	private ShopCarAdapter mAdapter;
	private LoadingDialog mLoadingDialog;
	private View mEmpeyView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_shopcar, container, false);
		shopcar_lv = (FlexiListView) mView.findViewById(R.id.shopcar_lv);
		mEmpeyView = mView.findViewById(R.id.null_view);
		shopcar_lv.setEmptyView(mEmpeyView);
		all_cbx = (CheckBox) mView.findViewById(R.id.all_cbx);
		all_money_tv = (TextView) mView.findViewById(R.id.all_money_tv);
		settle_tv = (TextView) mView.findViewById(R.id.settle_tv);

		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLoadingDialog = new LoadingDialog(getActivity());
		mAdapter = new ShopCarAdapter(getContext());
		shopcar_lv.setAdapter(mAdapter);
		shopcar_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mAdapter.setItemChecked(i);
			}
		});
		all_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				mAdapter.setItemAllChecked(b);
			}
		});
		//去结算
		settle_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mAdapter.isCheckOne()) {
					Intent intent = new Intent(getActivity(), SettleActivity.class);
					intent.putExtra("data", mAdapter.getChooseItem());
					startActivity(intent);
				}else {
					Toast.makeText(getActivity(), "至少选中一个商品", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mAdapter.setOnShopCarDataChangeListener(new ShopCarAdapter.OnShopCarDataChangeListener() {
			@Override
			public void dataChange(Object... values) {
				all_money_tv.setText("总额: ￥ " + values[0]);
				settle_tv.setText("去结算("+ values[1] +")");
			}

			@Override
			public void deleteItem(int id) {
				Toast.makeText(getActivity(), "del回调", Toast.LENGTH_SHORT).show();
				mController.sendAsyncMessage(IdiyMessage.DEL_SHOP_CAR, getUserId(), id);
			}
		});
		initController();
	}

	protected long getUserId() {
		return ((MyApplication)getActivity().getApplication()).getUserInfo().getId();
	}

	@Override
	protected void initController() {
		mController = new ShopCarController(getContext());
		mController.setModeChangeListener(this);
		mLoadingDialog.show();
		mController.sendAsyncMessage(IdiyMessage.SHOP_CAR_LIST, getUserId());
	}

	@Override
	protected void myHandleMessage(Message msg) {
		if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
		if(msg.obj == null) return;
		Object[] o = (Object[]) msg.obj;
		switch (msg.what) {
			case IdiyMessage.SHOP_CAR_LIST:
				handleShopCarListData((List<ShopCarBean>) o[0]);
				break;
			case IdiyMessage.DEL_SHOP_CAR:
				handleDelShopCar((RResult) o[0]);
				break;
		}
	}

	private void handleDelShopCar(RResult result) {
		if(result.isSuccess()) {
			Toast.makeText(getActivity(), "成功删除购物车商品", Toast.LENGTH_SHORT).show();
			mController.sendAsyncMessage(IdiyMessage.SHOP_CAR_LIST, getUserId());
		}else {
			Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
		}
	}

	private void handleShopCarListData(List<ShopCarBean> data) {
		mAdapter.setDatas(data);
		all_money_tv.setText("总额: ￥ " + mAdapter.getTotalPrice());
		settle_tv.setText("去结算("+ mAdapter.getTotalCount() +")");
	}

	public void update() {
		mController.sendAsyncMessage(IdiyMessage.SHOP_CAR_LIST, getUserId());
	}

	@Override
	public void onResume() {
		super.onResume();
		mController.sendAsyncMessage(IdiyMessage.SHOP_CAR_LIST, getUserId());
	}
}
