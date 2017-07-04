package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.WaitPayAdapter;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.Banner;
import com.viger.gfJdmall.bean.OrderBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.OrderController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * 待支付页
 * */
public class WaitPayFragment extends BaseFragment {

	private XListView wait_pay_lv;
	private View mView;
	private WaitPayAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_wait_pay, container,false);
		wait_pay_lv = (XListView) mView.findViewById(R.id.wait_pay_lv);
		wait_pay_lv.setPullRefreshEnable(true);
		wait_pay_lv.setPullLoadEnable(false);
		wait_pay_lv.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				mController.sendAsyncMessage(IdiyMessage.WAIT_PAY_ACTION, 0, getUserId());
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
		mAdapter = new WaitPayAdapter(getActivity());
		mAdapter.setOnOrderPayListener(new WaitPayAdapter.OnOrderPayListener() {
			@Override
			public void onPay(int position) {
				Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
			}
		});
		wait_pay_lv.setAdapter(mAdapter);
		initController();
		mController.sendAsyncMessage(IdiyMessage.WAIT_PAY_ACTION, 0, getUserId());
	}

	protected long getUserId() {
		return ((MyApplication)getActivity().getApplication()).getUserInfo().getId();
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
			case IdiyMessage.WAIT_PAY_ACTION:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					handleGetOrderByWaitPay((List<OrderBean>) o[0]);
				}
				break;
		}
	}

	private void handleGetOrderByWaitPay(List<OrderBean> data) {
		if(data != null && data.size()>0) {
			mAdapter.setDatas(data);
			wait_pay_lv.setRefreshTime(getCurrentTime());
			wait_pay_lv.stopRefresh();
		}
	}
}
