package com.viger.gfJdmall.application;

import android.app.Application;
import com.viger.gfJdmall.bean.RLoginResult;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MyApplication extends Application {

    private RLoginResult mUserInfo;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setUserInfo(RLoginResult rLoginResult) {
        this.mUserInfo = rLoginResult;
    }

    public RLoginResult getUserInfo() {
        return mUserInfo;
    }
}
