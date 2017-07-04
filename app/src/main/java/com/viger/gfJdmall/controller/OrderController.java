package com.viger.gfJdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Network;
import com.viger.gfJdmall.bean.OrderBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.utils.NetworkUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class OrderController extends BaseController {

    public OrderController(Context ctx) {
        super(ctx);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.WAIT_PAY_ACTION:
                mListener.onModeChange(action, getOrderByStatus((Integer)obj[0],(Long)obj[1]));
                break;
            case IdiyMessage.WAIT_RECEIVE_ACTION:
                mListener.onModeChange(action, getOrderByStatus((Integer)obj[0],(Long)obj[1]));
                break;
            case IdiyMessage.CONFIRM_ORDER:
                mListener.onModeChange(action, confirmOrder((Long)obj[0],obj[1].toString()));
                break;
        }
    }

    private RResult confirmOrder(long userId, String oid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId",userId+"");
        params.put("oid", oid);
        String json = NetworkUtil.doPost(NetworkConst.CONFIRM_ORDER, params);
        RResult result = JSON.parseObject(json, RResult.class);
        return result;
    }

    private List<OrderBean> getOrderByStatus(int status, long userId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("status",status+"");
        params.put("userId", userId+"");
        String json = NetworkUtil.doPost(NetworkConst.GET_ORDER_BY_STATUS, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), OrderBean.class);
        }
        return null;
    }
}
