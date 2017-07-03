package com.viger.gfJdmall.listener;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface IProductSortChangeListener {

    public static final int ALLSORT = 0;
    public static final int NEWSSORT = 1;
    public static final int COMMENTSORT = 2;

    public void onSortChanged(int action);

}
