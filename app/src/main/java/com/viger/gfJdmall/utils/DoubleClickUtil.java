package com.viger.gfJdmall.utils;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DoubleClickUtil {

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if(0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
