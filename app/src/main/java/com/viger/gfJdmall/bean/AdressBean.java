package com.viger.gfJdmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/14.
 */

public class AdressBean implements Serializable{


    /**
     * id : 地址id
     * isDefault : false
     * receiverName : 接收人
     * receiverAddress : 具体地址
     * receiverPhone : 手机号
     */

    private long id;
    private boolean isDefault;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
