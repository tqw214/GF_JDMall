package com.viger.gfJdmall.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.bean.BrandBean;
import com.viger.gfJdmall.bean.CategoryBean;
import com.viger.gfJdmall.bean.ProductListBean;
import com.viger.gfJdmall.bean.ProductSortBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.SubCategoryBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class CategoryController extends BaseController {

    public CategoryController(Context ctx) {
        super(ctx);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.CATEGORY_ACTION:
                mListener.onModeChange(IdiyMessage.CATEGORY_ACTION, getCategoryInfo());
                break;
            case IdiyMessage.SUB_CATEGORY_ACTION:
                int i = (int) obj[0];
                mListener.onModeChange(IdiyMessage.SUB_CATEGORY_ACTION, getSubCategoryInfo(i));
                break;
            case IdiyMessage.BRAND_ACTION:
                int j = (int) obj[0];
                mListener.onModeChange(IdiyMessage.BRAND_ACTION, getBrandList(j));
                break;
            case IdiyMessage.PRODUCT_LIST_ACTION:
                mListener.onModeChange(IdiyMessage.PRODUCT_LIST_ACTION, loadProductList((ProductSortBean) obj[0]));
                break;
        }
    }

    private List<ProductListBean> loadProductList(ProductSortBean bean) {
        List<ProductListBean> datas = new ArrayList<ProductListBean>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("categoryId", bean.getCategoryId()+"");
        params.put("filterType", bean.getFilterType()+"");
        if(bean.getSortType() != 0) {
            params.put("sortType", bean.getSortType()+"");
        }
        params.put("deliverChoose", bean.getDeliverChoose()+"");
        if(bean.getMinPrice() !=0 && bean.getMaxPrice() != 0) {
            params.put("minPrice", bean.getMinPrice()+"");
            params.put("maxPrice", bean.getMaxPrice()+"");
        }
        if(bean.getBrandId() != 0) {
            params.put("brandId", bean.getBrandId()+"");
        }
        String json = NetworkUtil.doPost(NetworkConst.PRODUCT_LIST_URL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            try {
                JSONObject object = new JSONObject(result.getResult());
                String rows = object.getString("rows");
                datas = JSON.parseArray(rows, ProductListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    private List<BrandBean> getBrandList(int level1Id) {
        List<BrandBean> dataList = new ArrayList<BrandBean>();
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("categoryId", level1Id + "");
        String json = NetworkUtil.doGet(NetworkConst.BRAND_URL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            dataList = JSON.parseArray(result.getResult(), BrandBean.class);
        }
        return dataList;
    }

    private List<SubCategoryBean> getSubCategoryInfo(long parentId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("parentId", parentId + "");
        String json = NetworkUtil.doGet(NetworkConst.CATEGORY_URL, map);
        Log.i("tag", json);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), SubCategoryBean.class);
        }
        return null;
    }

    private List<CategoryBean> getCategoryInfo() {
        String json = NetworkUtil.doGet(NetworkConst.CATEGORY_URL, null);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), CategoryBean.class);
        }
        return null;
    }

}
