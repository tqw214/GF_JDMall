package com.viger.gfJdmall.controller;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viger.gfJdmall.bean.Banner;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.RecommendBean;
import com.viger.gfJdmall.bean.SecondKillBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.fragment.BaseFragment;
import com.viger.gfJdmall.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HomeController extends BaseController {

    public HomeController(Context ctx) {
        super(ctx);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.GET_BANNER_ACTION:
                List<Banner> data = getBanner((Integer) obj[0]);
                mListener.onModeChange(IdiyMessage.GET_BANNER_ACTION, data);
                break;
            case IdiyMessage.SECOND_KILL_ACTION:
                mListener.onModeChange(IdiyMessage.SECOND_KILL_ACTION, getSecondKillInfo());
                break;
            case IdiyMessage.RECOMMEND_ACTION:
                mListener.onModeChange(IdiyMessage.RECOMMEND_ACTION, getRecommendInfo());
                break;
        }
    }

    private List<RecommendBean> getRecommendInfo() {
        List<RecommendBean> list = null;
        String json = NetworkUtil.doGet(NetworkConst.RECOMMEND_URL, null);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            String result1 = result.getResult();
            try {
                org.json.JSONObject ojb = new org.json.JSONObject(result1);
                return JSON.parseArray(ojb.getString("rows"), RecommendBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private SecondKillBean getSecondKillInfo() {
        SecondKillBean bean = null;
        String result = NetworkUtil.doGet(NetworkConst.SECONDKILL_URL, null);
        RResult res = JSON.parseObject(result, RResult.class);
        if(res != null && res.isSuccess()) {
            bean = JSON.parseObject(res.getResult(), SecondKillBean.class);
        }
        return bean;
    }

    private List<Banner> getBanner(int type) {
        List<Banner> datas = new ArrayList<Banner>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("adKind", type + "");
        String res = NetworkUtil.doGet(NetworkConst.BANNER_URL, params);
        RResult result = JSON.parseObject(res, RResult.class);
        if(result.isSuccess()) {
            datas = JSON.parseArray(result.getResult(), Banner.class);
        }
        return datas;
    }

}
