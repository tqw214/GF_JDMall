package com.viger.gfJdmall.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.viger.gfJdmall.listener.IProductSortChangeListener;

import java.security.PrivateKey;

/**
 * Created by Administrator on 2017/5/24.
 */

public abstract class IPopupWindowProtocal {

    protected PopupWindow mPopWindow;
    protected Context mContext;
    protected IProductSortChangeListener mListener;

    public void setProductSortChangeListener(IProductSortChangeListener listener) {
        this.mListener = listener;
    }

    public IPopupWindowProtocal( Context ctx) {
        this.mContext = ctx;
        initUI();
    }

    protected abstract void initUI();
    protected abstract void onShow(View view);

    public void onDismiss() {
        if(mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

}
