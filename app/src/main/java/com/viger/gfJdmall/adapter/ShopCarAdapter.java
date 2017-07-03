package com.viger.gfJdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.bean.ShopCarBean;
import com.viger.gfJdmall.cons.NetworkConst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/12.
 */

public class ShopCarAdapter extends JdBaseAdapter<ShopCarBean> {

    private OnShopCarDataChangeListener mListener;
    private Set<Integer> isCheckids;

    public interface OnShopCarDataChangeListener {
        void dataChange(Object... values);
        void deleteItem(int positon);
    }

    public void setOnShopCarDataChangeListener(OnShopCarDataChangeListener listener) {
        this.mListener = listener;
    }

    public ShopCarAdapter(Context ctx) {
        super(ctx);
        isCheckids = new HashSet<Integer>();
    }

    @Override
    protected View getViewBase(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = mInflater.inflate(R.layout.shopcar_lv_item, viewGroup, false);
            holder = new ViewHolder();
            holder.cbx = (CheckBox) view.findViewById(R.id.cbx);
            holder.product_iv = (SmartImageView) view.findViewById(R.id.product_iv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.version_tv = (TextView) view.findViewById(R.id.version_tv);
            holder.price_tv = (TextView) view.findViewById(R.id.price_tv);
            holder.buyCount_tv = (TextView) view.findViewById(R.id.buyCount_tv);
            holder.delete_product = (Button) view.findViewById(R.id.delete_product);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ShopCarBean rowsBean = mDatas.get(i);
        holder.product_iv.setImageUrl(NetworkConst.BASE_URL + rowsBean.getPimageUrl());
        holder.name_tv.setText(rowsBean.getPname() +"");
        holder.version_tv.setText(rowsBean.getPversion() + " ");
        holder.price_tv.setText("¥ "+rowsBean.getPprice()+"");
        holder.buyCount_tv.setText("x" + rowsBean.getBuyCount()+"");
        holder.delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteItem(mDatas.get(i).getId());
            }
        });
//        boolean isChecked = rowsBean.isChecked();
//        if(isChecked) {
//            holder.cbx.setChecked(true);
//        }else {
//            holder.cbx.setChecked(false);
//        }
        boolean b = isCheckids.contains(mDatas.get(i).getId());
        if(b) {
            holder.cbx.setChecked(true);
        }else {
            holder.cbx.setChecked(false);
        }
        return view;
    }

    public void setItemChecked(int i) {
        boolean isSelector = isCheckids.contains(mDatas.get(i).getId());
        if(isSelector) {
            isCheckids.remove(mDatas.get(i).getId());
        }else {
            isCheckids.add(mDatas.get(i).getId()); //记录1
        }
        this.notifyDataSetChanged();
        getTotalPriceAndCount();
    }

    public boolean isCheckOne() {
        if(isCheckids.size() > 0) {
            return true;
        }
        return false;
    }

    public void getTotalPriceAndCount() {
        if(mDatas != null && mDatas.size()>0) {
            mListener.dataChange(getTotalPrice(), getTotalCount());
        }
    }

    public float getTotalPrice() {
        float price = 0;
        for(ShopCarBean bean : mDatas) {
            if(isCheckids.contains(bean.getId())) {
                price += bean.getPprice();
            }
        }
        return price;
    }

    public int getTotalCount() {
        int count=0;
        for(ShopCarBean bean : mDatas) {
            if(isCheckids.contains(bean.getId())) {
                count++;
            }
        }
        return count;
    }

    public void setItemAllChecked(boolean b) {
        if(mDatas != null && mDatas.size()>0) {
            for(ShopCarBean bean : mDatas) {
                if(b) {
                    isCheckids.add(bean.getId());
                }else {
                    isCheckids.remove(bean.getId());
                }
            }
            this.notifyDataSetChanged();
            getTotalPriceAndCount();
        }
    }

    public ArrayList<ShopCarBean> getChooseItem() {
        ArrayList<ShopCarBean> list = new ArrayList<ShopCarBean>();
        if(mDatas != null && mDatas.size() > 0) {
            for(ShopCarBean bean : mDatas) {
                int id = bean.getId();
                if(isCheckids.contains(id)) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    class ViewHolder {
        CheckBox cbx;
        SmartImageView product_iv;
        TextView name_tv;
        TextView version_tv;
        TextView price_tv;
        TextView buyCount_tv;
        Button delete_product;
    }
}
