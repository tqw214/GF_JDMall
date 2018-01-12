package com.viger.gfJdmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.RLoginResult;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.UserInfo;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.UserController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/8.
 */

public class LoginActivity extends BaseActivity {

    private EditText name_et;
    private EditText pwd_et;
    private Button login_btn;
    private MyHandler mHandler;

    @Override
    protected void initView() {
        name_et = (EditText) findViewById(R.id.name_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        login_btn = (Button) findViewById(R.id.login_btn);

        mController = new UserController(this);
        mController.sendAsyncMessage(IdiyMessage.GET_USERFROMDB);
    }

    @Override
    protected void initController() {
        mController.setModeChangeListener(new IModeChangeListener() {
            @Override
            public void onModeChange(int action, Object... data) {
                //Message message = mHandler.obtainMessage();
                //message.obj = data[0];
                //message.what = action;
                //mHandler.sendMessage(message);
                mHandler.obtainMessage(action, data[0]).sendToTarget();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler = new MyHandler(this);
        initView();
        initController();
    }

    private static class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;
        public MyHandler(Activity act) {
            this.mActivity = new WeakReference<Activity>(act);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity act = (LoginActivity) mActivity.get();
            if(act == null) return;
            switch (msg.what) {
                case IdiyMessage.LOGIN_ACTION:
                    RResult result = (RResult) msg.obj;
                    if(result.isSuccess()) {
                        //登录成功
                        //act.tip("登录成功");
                        //Intent intent = new Intent(act, MainActivity.class);
                        //act.startActivity(intent);
                        //act.finish();

                        //将用户信息保存到application中
                        RLoginResult rLoginResult = JSON.parseObject(result.getResult(), RLoginResult.class);
                        MyApplication application = (MyApplication) act.getApplication();
                        application.setUserInfo(rLoginResult);

                        //保存用户名密码到数据库
                        String name = act.name_et.getText().toString().trim();
                        String pwd = act.pwd_et.getText().toString().trim();
                        act.mController.sendAsyncMessage(IdiyMessage.SAVE_USERTODB, name, pwd);
                    }else {
                        //登录失败
                        act.tip("登录失败:" + result.getErrorMsg());
                        act.clearEditText();
                    }
                    break;
                case IdiyMessage.SAVE_USERTODB:
                    boolean reuslt = (boolean) msg.obj;
                    if(reuslt) {
                        act.tip("登录成功");
                        Intent intent = new Intent(act, MainActivity.class);
                        act.startActivity(intent);
                        act.finish();
                    }else {
                        act.tip("登录异常");
                    }
                    break;
                case IdiyMessage.GET_USERFROMDB:
                    if(msg.obj != null) {
                        UserInfo userInfo = (UserInfo) msg.obj;
                        if(act.name_et != null) act.name_et.setText(userInfo.getName());
                        if(act.pwd_et != null) act.pwd_et.setText(userInfo.getPwd());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void clearEditText() {
        if(name_et != null) name_et.setText("");
        if(pwd_et != null) pwd_et.setText("");
        name_et.requestFocus();
    }

    //注册用户
    public void registClick(View view) {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivity(intent);
    }

    //登录
    public void loginClick(View view) {
        //if(DoubleClickUtil.isFastDoubleClick()) {
            //return;
        //}
        String name = name_et.getText().toString().trim();
        String pwd = pwd_et.getText().toString().trim();
        if(isValuesEmpty(name, pwd)) {
            tip("请输入用户名密码");
            return;
        }
        mController.sendAsyncMessage(IdiyMessage.LOGIN_ACTION, name, pwd);
    }

    private Response.Listener<String> mListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

        }
    };

    //重置密码
    public void resetPwdClick(View view) {

    }

}
