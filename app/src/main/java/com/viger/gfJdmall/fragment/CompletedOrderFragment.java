package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.CompleteOrderAdapter;
import com.viger.gfJdmall.adapter.WaitReceiveAdapter;
import com.viger.gfJdmall.bean.OrderBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.OrderController;

import java.util.List;

import me.maxwin.view.XListView;

/**
 * 已完成页
 * */
public class CompletedOrderFragment extends BaseFragment {

	private XListView complete_lv;
	private View mView;
	private CompleteOrderAdapter mAdapter;
	private List<OrderBean> mDatas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_completed_order, container,false);
		complete_lv = (XListView) mView.findViewById(R.id.comment_lv);
		complete_lv.setPullRefreshEnable(true);
		complete_lv.setPullLoadEnable(false);
		complete_lv.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				mController.sendAsyncMessage(IdiyMessage.COMPLETE_ORDER, 3, getUserId());
			}

			@Override
			public void onLoadMore() {

			}
		});
		return mView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new CompleteOrderAdapter(getActivity());
		complete_lv.setAdapter(mAdapter);
		initController();
		mController.sendAsyncMessage(IdiyMessage.COMPLETE_ORDER, 3, getUserId());
	}

	@Override
	protected void initController() {
		super.initController();
		mController = new OrderController(getActivity());
		mController.setModeChangeListener(this);
	}

	@Override
	protected void myHandleMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.COMPLETE_ORDER:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					handleCompleteOrder((List<OrderBean>) o[0]);
				}
				break;
		}
	}

	private void handleCompleteOrder(List<OrderBean> result) {
		if(result != null && result.size()>0) {
			mAdapter.setDatas(result);
		}
	}
}
