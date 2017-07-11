package com.viger.gfJdmall.application;

import android.app.Application;
import com.viger.gfJdmall.bean.RLoginResult;
import com.viger.gfJdmall.utils.ErrorReport;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MyApplication extends Application {

    private RLoginResult mUserInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        ErrorReport mErrorReport = ErrorReport.getInstance();
        mErrorReport.init(this);
    }

    public void setUserInfo(RLoginResult rLoginResult) {
        this.mUserInfo = rLoginResult;
    }

    public RLoginResult getUserInfo() {
        return mUserInfo;
    }
}
