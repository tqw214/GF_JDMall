package com.viger.gfJdmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import com.viger.gfJdmall.controller.BaseController;
import com.viger.gfJdmall.listener.IModeChangeListener;

public abstract class BaseFragment extends Fragment implements IModeChangeListener {

    protected BaseController mController;

    protected abstract void myHandleMessage(Message msg);
    protected void initController(){
        //default empty
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandler.obtainMessage(action, data).sendToTarget();
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myHandleMessage(msg);
        }
    };

}
