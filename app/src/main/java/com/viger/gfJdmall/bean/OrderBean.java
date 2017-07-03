package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class OrderBean {


    /**
     * items : ["商品图片"]
     * oid : 订单id
     * orderNum : 订单号
     * paid : 是否支付
     * status : 订单状态
     * tn : 订单令牌
     * totalPrice : 订单总金额
     */

    private String oid;
    private String orderNum;
    private String paid;
    private String status;
    private String tn;
    private String totalPrice;
    private List<String> items;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
