package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.WaitReceiveAdapter;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.OrderBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.OrderController;
import java.util.List;
import me.maxwin.view.XListView;

/**
 * 待收货页
 * */
public class WaitReceiveFragment extends BaseFragment {

	private View mView;
	private XListView wait_pay_lv;
	private WaitReceiveAdapter mAdapter;
	private List<OrderBean> mDatas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_wait_receive, container,false);
		wait_pay_lv = (XListView) mView.findViewById(R.id.wait_receive_lv);
		wait_pay_lv.setPullRefreshEnable(true);
		wait_pay_lv.setPullLoadEnable(false);
		wait_pay_lv.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				mController.sendAsyncMessage(IdiyMessage.WAIT_RECEIVE_ACTION, 2, getUserId());
			}

			@Override
			public void onLoadMore() {

			}
		});
		return mView;
	}

	protected long getUserId() {
		return ((MyApplication)getActivity().getApplication()).getUserInfo().getId();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new WaitReceiveAdapter(getActivity());
		wait_pay_lv.setAdapter(mAdapter);
		mAdapter.setOnOrderReceiveListener(new WaitReceiveAdapter.OnOrderReceiveListener() {
			@Override
			public void onReceive(int position) {
				//确认收货
				mController.sendAsyncMessage(IdiyMessage.CONFIRM_ORDER, getUserId(), mDatas.get(position).getOid());
			}
		});
		initController();
		mController.sendAsyncMessage(IdiyMessage.WAIT_RECEIVE_ACTION, 2, getUserId());
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
			case IdiyMessage.WAIT_RECEIVE_ACTION:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					handleGetOrderByWaitReceive((List<OrderBean>) o[0]);
				}
				break;
			case IdiyMessage.CONFIRM_ORDER:
				if(msg.obj != null) {
					Object[] o = (Object[]) msg.obj;
					handleConfirmOrder((RResult) o[0]);
				}
				break;
		}
	}

	private void handleConfirmOrder(RResult result) {
		if(result.isSuccess()) {
			showToast("收货成功");
			mController.sendAsyncMessage(IdiyMessage.WAIT_RECEIVE_ACTION, 2, getUserId());
		}else {
			showToast(result.getErrorMsg());
		}
	}

	private void handleGetOrderByWaitReceive(List<OrderBean> data) {
		if(data != null) {
			this.mDatas = data;
			mAdapter.setDatas(data);
			wait_pay_lv.setRefreshTime(getCurrentTime());
		}
		wait_pay_lv.stopRefresh();
	}
}
