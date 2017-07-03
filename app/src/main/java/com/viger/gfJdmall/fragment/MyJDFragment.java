package com.viger.gfJdmall.fragment;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.activity.LoginActivity;
import com.viger.gfJdmall.activity.OderListActivity;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.RLoginResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.UserController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.utils.ActivityUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyJDFragment extends BaseFragment {

	private View mView;
	private Button logout_btn;
	private TextView user_name_tv;
	private TextView user_level_tv;
	private TextView wait_pay_tv;
	private TextView wait_receive_tv;
	private ImageView user_icon_iv;

	private LinearLayout mime_order; //全部订单
	private MyApplication myApplication;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_myjd, container, false);
		initController();
		return mView;
	}

	@Override
	protected void myHandleMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.CLEAR_USER_ACTION:
				ActivityUtils.start(getActivity(), LoginActivity.class, true);
				break;
		}
	}

	private void isLoginOut() {
		new AlertDialog.Builder(getActivity())
				.setTitle("提示信息")
				.setMessage("是否退出登录?")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						mController.sendAsyncMessage(IdiyMessage.CLEAR_USER_ACTION);
					}
				})
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			}).show();
	}

	@Override
	protected void initController() {
		mController = new UserController(getActivity());
		mController.setModeChangeListener(this);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myApplication = (MyApplication) getActivity().getApplication();
		RLoginResult userInfo = myApplication.getUserInfo();
		logout_btn = (Button) mView.findViewById(R.id.logout_btn);
		user_name_tv = (TextView) mView.findViewById(R.id.user_name_tv);
		user_name_tv.setText(userInfo.getUserName());
		user_level_tv = (TextView) mView.findViewById(R.id.user_level_tv);
		user_level_tv.setText(getUserLevel(userInfo.getUserLevel()));
		wait_pay_tv = (TextView) mView.findViewById(R.id.wait_pay_tv);
		wait_pay_tv.setText(userInfo.getWaitPayCount() + "");
		wait_receive_tv = (TextView) mView.findViewById(R.id.wait_receive_tv);
		wait_receive_tv.setText(userInfo.getWaitReceiveCount() + "");
		user_icon_iv = (ImageView) mView.findViewById(R.id.user_icon_iv);
		mime_order = (LinearLayout) mView.findViewById(R.id.mime_order);
		mime_order.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//全部订单列表
				ActivityUtils.start(getActivity(), OderListActivity.class, false);
			}
		});
		logout_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				isLoginOut();
			}
		});


	}

	private String getUserLevel(int level) {
		String name = "注册会员";
		switch (level) {
			case 1:
				name = "注册会员";
				break;
			case 2:
				name = "铜牌会员";
				break;
			case 3:
				name = "银牌会员";
				break;
			case 4:
				name = "金牌会员";
				break;
			case 5:
				name = "钻石会员";
				break;
		}
		return name;
	}

}
