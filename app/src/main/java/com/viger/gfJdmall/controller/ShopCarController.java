package com.viger.gfJdmall.controller;

import android.content.Context;
import android.provider.SyncStateContract;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.viger.gfJdmall.bean.AddOrderResultBean;
import com.viger.gfJdmall.bean.AdressBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.ReceviceAdressBean;
import com.viger.gfJdmall.bean.ShopCarBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.utils.NetworkUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ShopCarController extends BaseController {

    public ShopCarController(Context ctx) {
        super(ctx);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.SHOP_CAR_LIST:
                mListener.onModeChange(action, getShopCarListData((Long) obj[0]));
                break;
            case IdiyMessage.DEL_SHOP_CAR:
                mListener.onModeChange(action, delShopCar((Long)obj[0],(Integer) obj[1]));
                break;
            case IdiyMessage.RECEIVE_DEFAULT_ADDRESS:
                mListener.onModeChange(action, receiveAddress((Long)obj[0], (Boolean) obj[1]));
                break;
            case IdiyMessage.GET_PROVINCE:
                mListener.onModeChange(action, getProvinces());
                break;
            case IdiyMessage.GET_CITY:
                mListener.onModeChange(action, getCitys(obj[0].toString()));
                break;
            case IdiyMessage.GET_AREA:
                mListener.onModeChange(action, getAreas(obj[0].toString()));
                break;
            case IdiyMessage.ADD_ADDRESS:
                mListener.onModeChange(action, addAddress((Long)obj[0], obj[1].toString(), obj[2].toString(), obj[3].toString(),
                        obj[4].toString(),obj[5].toString(),obj[6].toString(), (Boolean)obj[7]));
                break;
            case IdiyMessage.CHOOSE_ADDRESS:
                mListener.onModeChange(action, chooseAddress((Long)obj[0], (Boolean)obj[1]));
                break;
            case IdiyMessage.DEL_ADDRESS:
                mListener.onModeChange(action, delAddress((long)obj[0],(long)obj[1]));
                break;
            case IdiyMessage.ADD_ORDER:
                mListener.onModeChange(action, addOrder((String) obj[0]));
                break;
        }
    }

    private RResult addOrder(String json) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("detail", json);
        String resultStr = NetworkUtil.doPost(NetworkConst.ADD_ORDER, params);
        return JSON.parseObject(resultStr, RResult.class);
    }

    private RResult delAddress(long userId, long id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id+"");
        params.put("userId", userId+"");
        String json = NetworkUtil.doPost(NetworkConst.DEL_ADDRESS, params);
        return JSON.parseObject(json, RResult.class);
    }

    private List<AdressBean> chooseAddress(long userId, boolean isDefault) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("userId", userId+"");
            params.put("isDefault", isDefault+"");
            String json = NetworkUtil.doPost(NetworkConst.RECEIVE_ADDRESS_URL, params);
            RResult result = JSON.parseObject(json, RResult.class);
            if(result.isSuccess()) {
                return JSON.parseArray(result.getResult(), AdressBean.class);
            }
            return null;
    }

    private RResult addAddress(long userId, String name, String phone, String provinceCode, String cityCode,
                                  String distCode, String address, boolean isDefault) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId+"");
        params.put("name", name);
        params.put("phone",phone);
        params.put("provinceCode", provinceCode);
        params.put("cityCode", cityCode);
        params.put("distCode", distCode);
        params.put("addressDetails", address);
        params.put("isDefault", isDefault+"");
        String json = NetworkUtil.doPost(NetworkConst.ADD_ADDRESS, params);
        return  JSON.parseObject(json, RResult.class);
    }

    private List<ReceviceAdressBean> getProvinces() {
        String json = NetworkUtil.doGet(NetworkConst.GET_PROVINCE, null);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), ReceviceAdressBean.class);
        }
        return null;
    }

    private List<ReceviceAdressBean> getCitys(String fcode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fcode", fcode);
        String json = NetworkUtil.doGet(NetworkConst.GET_CITY, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), ReceviceAdressBean.class);
        }
        return null;
    }

    private List<ReceviceAdressBean> getAreas(String fcode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fcode", fcode);
        String json = NetworkUtil.doGet(NetworkConst.GET_AREA, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), ReceviceAdressBean.class);
        }
        return null;
    }

    private AdressBean receiveAddress(long userId, boolean isDefault) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId+"");
        params.put("isDefault", isDefault+"");
        String json = NetworkUtil.doPost(NetworkConst.RECEIVE_ADDRESS_URL, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            List<AdressBean> list = JSON.parseArray(result.getResult(), AdressBean.class);
            if(list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    private RResult delShopCar(long userId, int showCarId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId+"");
        params.put("id", showCarId+"");
        String json = NetworkUtil.doPost(NetworkConst.DEL_SHOP_CAR, params);
        return JSON.parseObject(json, RResult.class);
    }

    private List<ShopCarBean> getShopCarListData(long userId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId+"");
        String json = NetworkUtil.doPost(NetworkConst.SHOP_CAR_LIST, params);
        RResult result = JSON.parseObject(json, RResult.class);
        if(result.isSuccess()) {
            return JSON.parseArray(result.getResult(), ShopCarBean.class);
        }
        return null;
    }
}
