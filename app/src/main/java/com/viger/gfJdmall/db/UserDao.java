package com.viger.gfJdmall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.viger.gfJdmall.bean.UserInfo;
import com.viger.gfJdmall.cons.DbConst;

/**
 * Created by Administrator on 2017/5/10.
 */

public class UserDao {

    private DbOpenHelper mHelper;

    public UserDao(Context ctx) {
        mHelper = new DbOpenHelper(ctx);
    }

    public boolean saveUser(String name, String pwd) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConst._NAME, name);
        values.put(DbConst._PWD, pwd);
        long insertId = db.insert(DbConst.USER_TABLE, null, values);
        db.close();
        return insertId != -1;
    }

    public void clearUser() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DbConst.USER_TABLE, null, null);
        db.close();
    }

    public UserInfo getUser() {
        UserInfo userInfo = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor query = db.query(DbConst.USER_TABLE, new String[]{DbConst._NAME, DbConst._PWD},
                null, null, null, null, null);
        if(query.moveToFirst()) {
            String name = query.getString(query.getColumnIndex(DbConst._NAME));
            String pwd = query.getString(query.getColumnIndex(DbConst._PWD));
            userInfo = new UserInfo();
            userInfo.setName(name);
            userInfo.setPwd(pwd);
        }
        db.close();
        return userInfo;
    }

    public void clearUsers() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DbConst.USER_TABLE, null, null);
        db.close();
    }

}
