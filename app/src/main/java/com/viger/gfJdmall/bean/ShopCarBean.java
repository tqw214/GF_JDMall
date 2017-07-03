package com.viger.gfJdmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ShopCarBean implements Serializable{


    /**
     * id : 购物车明细id
     * buyCount : 购买数
     * storeName : 商店名称
     * pprice : 价格
     * pimageUrl : 商品图片路径
     * pname : 商品名称
     * pid : 商品id
     * stockCount : 库存
     * storeId : 商店id
     * pversion : 商品版本
     */

    private int id;
    private int buyCount;
    private String storeName;
    private float pprice;
    private String pimageUrl;
    private String pname;
    private int pid;
    private int stockCount;
    private int storeId;
    private String pversion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getPprice() {
        return pprice;
    }

    public void setPprice(float pprice) {
        this.pprice = pprice;
    }

    public String getPimageUrl() {
        return pimageUrl;
    }

    public void setPimageUrl(String pimageUrl) {
        this.pimageUrl = pimageUrl;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getPversion() {
        return pversion;
    }

    public void setPversion(String pversion) {
        this.pversion = pversion;
    }
}
