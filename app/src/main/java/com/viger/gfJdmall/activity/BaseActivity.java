package com.viger.gfJdmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.controller.BaseController;

/**
 * Created by Administrator on 2017/5/8.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected BaseController mController;

    protected abstract void initView();
    protected void initController(){
        //default empty
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void tip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected boolean isValuesEmpty(String... values) {
        for(String s : values) {
            if(TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

//    protected long getUserId() {
//        return ((MyApplication)getApplication()).getUserInfo().getId();
//    }


}
