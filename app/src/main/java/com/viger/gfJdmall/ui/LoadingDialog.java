package com.viger.gfJdmall.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.viger.gfJdmall.R;

/**
 * Created by Administrator on 2017/7/11.
 */

public class LoadingDialog extends AlertDialog {

    private ImageView loading_iv;

    public LoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_view);
        loading_iv = (ImageView) findViewById(R.id.loading_iv);
        AnimationDrawable animationDrawable = (AnimationDrawable) loading_iv.getDrawable();
        animationDrawable.start();
    }
}
