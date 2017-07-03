package com.viger.gfJdmall.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.listener.IProductSortChangeListener;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ProductSortPopWindow extends IPopupWindowProtocal implements View.OnClickListener {

    private View mView;
    private TextView all_sort;
    private TextView new_sort;
    private TextView comment_sort;

    public ProductSortPopWindow(Context ctx) {
        super(ctx);
    }

    public void initUI() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.product_sort_pop_view, null);
        all_sort = (TextView) mView.findViewById(R.id.all_sort);
        new_sort = (TextView) mView.findViewById(R.id.new_sort);
        comment_sort = (TextView) mView.findViewById(R.id.comment_sort);
        all_sort.setOnClickListener(this);
        new_sort.setOnClickListener(this);
        comment_sort.setOnClickListener(this);
        mPopWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setContentView(mView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.update();
    }

    @Override
    public void onShow(View view) {
        if(mPopWindow!=null) {
            mPopWindow.showAsDropDown(view);
        }
    }


    @Override
    public void onClick(View view) {
        super.onDismiss();
        switch (view.getId()) {
            case R.id.all_sort:
                mListener.onSortChanged(IProductSortChangeListener.ALLSORT);
                break;
            case R.id.new_sort:
                mListener.onSortChanged(IProductSortChangeListener.NEWSSORT);
                break;
            case R.id.comment_sort:
                mListener.onSortChanged(IProductSortChangeListener.COMMENTSORT);
                break;

        }
    }
}
