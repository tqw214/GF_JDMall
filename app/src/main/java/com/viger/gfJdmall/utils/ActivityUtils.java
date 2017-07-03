package com.viger.gfJdmall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.viger.gfJdmall.activity.BaseActivity;

/**
 * Created by Administrator on 2017/5/11.
 */

public class ActivityUtils {

    public static void start(Activity ctx, Class<? extends BaseActivity> clazz, boolean isFinishSelf) {
        Intent intent = new Intent();
        intent.setClass(ctx, clazz);
        ctx.startActivity(intent);
        if(isFinishSelf) {
            ctx.finish();
        }
    }

}
