package com.viger.gfJdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.bean.CommentCountBean;
import com.viger.gfJdmall.bean.CommentDetailBean;
import com.viger.gfJdmall.bean.GoodCommentBean;
import com.viger.gfJdmall.bean.ProductInfoBean;
import com.viger.gfJdmall.bean.ProductListBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.fragment.ProductCommentFragment;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ProductController extends BaseController {

    public ProductController(Context ctx) {
        super(ctx);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.PRODUCT_INFO_ACTION:
                mListener.onModeChange(action, getProductInfo((Long) obj[0]));
                break;
            case IdiyMessage.GOOD_COMMENT_ACTION_RESULT:
                mListener.onModeChange(action, loadGoodComment((Long) obj[0]));
                break;
            case IdiyMessage.GET_COMMENT_COUNT_ACTION:
                mListener.onModeChange(action, getCommentCount((Long)obj[0]));
                break;
            case IdiyMessage.GET_COMMENT_DETAIL:
                mListener.onModeChange(action, getCommentDetail((Long)obj[0], (Integer)obj[1]));
                break;
            case IdiyMessage.TO_SHOP_CAR:
                mListener.onModeChange(action, toShopCar((Long) obj[0],(Long) obj[1],(Integer) obj[2],obj[3].toString()));
                break;
        }
    }

    private RResult toShopCar(long userId, long pId, int count, String pversion) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId+"");
        params.put("productId", pId+"");
        params.put("buyCount",count+"");
        params.put("pversion", pversion);
        String json = NetworkUtil.doPost(NetworkConst.TO_SHOP_CAR, params);
        return JSON.parseObject(json, RResult.class);
    }

    private List<CommentDetailBean> getCommentDetail(long pid, int type) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("productId",pid+"");
        if(type == ProductCommentFragment.HASIMAGE_COMMENT) {
            params.put("hasImgCom","true");
            params.put("type",ProductCommentFragment.ALL_COMMENT+"");
        }else {
            params.put("type",type+"");
        }
        String json = NetworkUtil.doPost(NetworkConst.COMMENTDETAIL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), CommentDetailBean.class);
        }
        return null;
    }

    private CommentCountBean getCommentCount(long pid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("productId", pid+"");
        String json = NetworkUtil.doPost(NetworkConst.GETCOMMENTCOUNT, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseObject(result.getResult(), CommentCountBean.class);
        }
        return null;
    }

    private List<GoodCommentBean> loadGoodComment(long pid) {
        List<GoodCommentBean> bean = null;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("productId", pid +"");
        params.put("type", "1");
        String json = NetworkUtil.doPost(NetworkConst.PRODUCTCOMMENT_URL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            bean = JSON.parseArray(result.getResult(), GoodCommentBean.class);
        }
        return bean;
    }

    private ProductInfoBean getProductInfo(long pId) {
        ProductInfoBean bean = null;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", pId + "");
        String json = NetworkUtil.doGet(NetworkConst.PRODUCT_INFO_URL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            bean = JSON.parseObject(result.getResult(), ProductInfoBean.class);
        }
        return bean;
    }

}
