package com.viger.gfJdmall.controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viger.gfJdmall.listener.IModeChangeListener;

/**
 * Created by Administrator on 2017/5/8.
 */

public abstract class BaseController {

    protected Context mContext;

    public BaseController(Context ctx) {
        this.mContext = ctx;
    }

    protected abstract void handleMessage(int action, Object... obj);

    protected IModeChangeListener mListener;

    public void setModeChangeListener(IModeChangeListener listener) {
        this.mListener = listener;
    }

    public void sendAsyncMessage(final int action, final Object... obj){
        new Thread(){
            @Override
            public void run() {
                handleMessage(action, obj);
            }
        }.start();
    }

}
