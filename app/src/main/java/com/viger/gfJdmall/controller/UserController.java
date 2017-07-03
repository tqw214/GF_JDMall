package com.viger.gfJdmall.controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.bean.UserInfo;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.cons.NetworkConst;
import com.viger.gfJdmall.db.UserDao;
import com.viger.gfJdmall.utils.AESUtils;
import com.viger.gfJdmall.utils.NetworkUtil;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/8.
 */

public class UserController extends BaseController {

    private UserDao mUserDao;

    public UserController(Context ctx) {
        super(ctx);
        mUserDao = new UserDao(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... obj) {
        switch (action) {
            case IdiyMessage.LOGIN_ACTION:
                RResult result = loginOrRegist(NetworkConst.LOGIN_URL, obj[0].toString(), obj[1].toString());
                if(mListener != null) {
                    mListener.onModeChange(IdiyMessage.LOGIN_ACTION, result);
                }
                break;
            case IdiyMessage.REGIST_ACTION:
                RResult result2 = loginOrRegist(NetworkConst.REGIST_URL, obj[0].toString(), obj[1].toString());
                mListener.onModeChange(IdiyMessage.REGIST_ACTION, result2);
                break;
            case IdiyMessage.SAVE_USERTODB:
                boolean b = saveUserToDb(obj[0].toString(), obj[1].toString());
                mListener.onModeChange(IdiyMessage.SAVE_USERTODB, b);
                break;
            case IdiyMessage.GET_USERFROMDB:
                UserInfo userFromDb = getUserFromDb();
                if(userFromDb != null) {
                    mListener.onModeChange(IdiyMessage.GET_USERFROMDB, userFromDb);
                }
                break;
            case IdiyMessage.CLEAR_USER_ACTION:
                clearUser();
                mListener.onModeChange(IdiyMessage.CLEAR_USER_ACTION);
                break;
        }
    }

    private void clearUser() {
        mUserDao.clearUsers();
    }

    private UserInfo getUserFromDb() {
        UserInfo userInfo = mUserDao.getUser();
        try {
            String name = AESUtils.decrypt(userInfo.getName());
            String pwd = AESUtils.decrypt(userInfo.getPwd());
            userInfo.setName(name);
            userInfo.setPwd(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    private boolean saveUserToDb(String name, String pwd) {
        mUserDao.clearUsers();
        try {
            name = AESUtils.encrypt(name);
            pwd = AESUtils.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUserDao.saveUser(name, pwd);
    }

    private RResult loginOrRegist(String url, String name, String pwd) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", name);
        map.put("pwd", pwd);
        String result = NetworkUtil.doPost(url, map);
        Log.i("tag", "==>"+result);
        if(!TextUtils.isEmpty(result)) {
            return JSON.parseObject(result, RResult.class);
        }
        return null;
    }

}
