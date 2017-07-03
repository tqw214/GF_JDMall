package com.viger.gfJdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.UserController;
import com.viger.gfJdmall.listener.IModeChangeListener;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RegistActivity extends BaseActivity {

    private EditText username_et,pwd_et,surepwd_et;
    private CheckBox cb_xieyi;

    @Override
    protected void initView() {
        username_et = (EditText) findViewById(R.id.username_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        surepwd_et = (EditText) findViewById(R.id.surepwd_et);
        cb_xieyi = (CheckBox) findViewById(R.id.cb_xieyi);
    }

    @Override
    protected void initController() {
        mController = new UserController(this);
        mController.setModeChangeListener(new IModeChangeListener() {
            @Override
            public void onModeChange(int action, Object... data) {
                mHandler.obtainMessage(action, data[0]).sendToTarget();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
        initController();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.REGIST_ACTION:
                    RResult result = (RResult) msg.obj;
                    if(result.isSuccess()) {
                        tip("注册成功");
                        finish();
                    }else {
                        tip(result.getErrorMsg());
                        clearEditText();
                    }
                    break;
            }
        }
    };

    private void clearEditText() {
        if(username_et != null) username_et.setText("");
        if(pwd_et != null) pwd_et.setText("");
        if(surepwd_et != null) surepwd_et.setText("");
        username_et.requestFocus();
    }

    //下一步
    public void registClick(View v) {
        String username = username_et.getText().toString().trim();
        String pwd = pwd_et.getText().toString().trim();
        String surepwd = surepwd_et.getText().toString().trim();
        if(isValuesEmpty(username, pwd, surepwd)) {
            tip("请输入完整信息");
            return;
        }
        if(!pwd.equals(surepwd)) {
            tip("两次输入密码不一致");
            return;
        }
        if(!cb_xieyi.isChecked()) {
            tip("必须同意协议");
            return;
        }
        mController.sendAsyncMessage(IdiyMessage.REGIST_ACTION, username, pwd);

    }

}
