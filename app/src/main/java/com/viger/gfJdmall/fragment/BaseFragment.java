package com.viger.gfJdmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.controller.BaseController;
import com.viger.gfJdmall.listener.IModeChangeListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseFragment extends Fragment implements IModeChangeListener {

    protected BaseController mController;

    protected abstract void myHandleMessage(Message msg);
    protected void initController(){
        //default empty
    }

    protected long getUserId() {
        return ((MyApplication)getActivity().getApplication()).getUserInfo().getId();
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandler.obtainMessage(action, data).sendToTarget();
    }

    protected String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myHandleMessage(msg);
        }
    };

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
