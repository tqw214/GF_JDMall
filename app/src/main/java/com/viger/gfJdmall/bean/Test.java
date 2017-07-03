package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class Test {


    /**
     * accessFailedCount : 0
     * answer : sz
     * bankName : 李四
     * claims : []
     * emailConfirmed : false
     * id : US24618
     * jAmount : 0.0
     * kAmount : 0.0
     * lockoutEnabled : false
     * logins : []
     * nickName : ja01
     * passwordHash : 50C215BB3B599960
     * phoneNumberConfirmed : false
     * qsoid : 1
     * remainPassword : 50C215BB3B599960
     * roles : []
     * sAmount : 0.0
     * states : 0
     * twoFactorEnabled : false
     * ugid : UG3
     * uglimit : {"ganggan":152,"hbid":"1","id":116,"minDistance":200,"minnum":3,"ordelay":2,"outBuy":3,"outSell":4,"pianyi":35,"states":0,"ugid":"UG3","wcprice":50}
     * userName : ja01
     * wAmount : 0.0
     */

    private int accessFailedCount;
    private String answer;
    private String bankName;
    private boolean emailConfirmed;
    private String id;
    private double jAmount;
    private double kAmount;
    private boolean lockoutEnabled;
    private String nickName;
    private String passwordHash;
    private boolean phoneNumberConfirmed;
    private int qsoid;
    private String remainPassword;
    private double sAmount;
    private int states;
    private boolean twoFactorEnabled;
    private String ugid;
    private UglimitBean uglimit;
    private String userName;
    private double wAmount;
    private List<?> claims;
    private List<?> logins;
    private List<?> roles;

    public int getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(int accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getJAmount() {
        return jAmount;
    }

    public void setJAmount(double jAmount) {
        this.jAmount = jAmount;
    }

    public double getKAmount() {
        return kAmount;
    }

    public void setKAmount(double kAmount) {
        this.kAmount = kAmount;
    }

    public boolean isLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(boolean lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public int getQsoid() {
        return qsoid;
    }

    public void setQsoid(int qsoid) {
        this.qsoid = qsoid;
    }

    public String getRemainPassword() {
        return remainPassword;
    }

    public void setRemainPassword(String remainPassword) {
        this.remainPassword = remainPassword;
    }

    public double getSAmount() {
        return sAmount;
    }

    public void setSAmount(double sAmount) {
        this.sAmount = sAmount;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getUgid() {
        return ugid;
    }

    public void setUgid(String ugid) {
        this.ugid = ugid;
    }

    public UglimitBean getUglimit() {
        return uglimit;
    }

    public void setUglimit(UglimitBean uglimit) {
        this.uglimit = uglimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getWAmount() {
        return wAmount;
    }

    public void setWAmount(double wAmount) {
        this.wAmount = wAmount;
    }

    public List<?> getClaims() {
        return claims;
    }

    public void setClaims(List<?> claims) {
        this.claims = claims;
    }

    public List<?> getLogins() {
        return logins;
    }

    public void setLogins(List<?> logins) {
        this.logins = logins;
    }

    public List<?> getRoles() {
        return roles;
    }

    public void setRoles(List<?> roles) {
        this.roles = roles;
    }

    public static class UglimitBean {
        /**
         * ganggan : 152
         * hbid : 1
         * id : 116
         * minDistance : 200
         * minnum : 3
         * ordelay : 2
         * outBuy : 3
         * outSell : 4
         * pianyi : 35
         * states : 0
         * ugid : UG3
         * wcprice : 50
         */

        private int ganggan;
        private String hbid;
        private int id;
        private int minDistance;
        private int minnum;
        private int ordelay;
        private int outBuy;
        private int outSell;
        private int pianyi;
        private int states;
        private String ugid;
        private int wcprice;

        public int getGanggan() {
            return ganggan;
        }

        public void setGanggan(int ganggan) {
            this.ganggan = ganggan;
        }

        public String getHbid() {
            return hbid;
        }

        public void setHbid(String hbid) {
            this.hbid = hbid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMinDistance() {
            return minDistance;
        }

        public void setMinDistance(int minDistance) {
            this.minDistance = minDistance;
        }

        public int getMinnum() {
            return minnum;
        }

        public void setMinnum(int minnum) {
            this.minnum = minnum;
        }

        public int getOrdelay() {
            return ordelay;
        }

        public void setOrdelay(int ordelay) {
            this.ordelay = ordelay;
        }

        public int getOutBuy() {
            return outBuy;
        }

        public void setOutBuy(int outBuy) {
            this.outBuy = outBuy;
        }

        public int getOutSell() {
            return outSell;
        }

        public void setOutSell(int outSell) {
            this.outSell = outSell;
        }

        public int getPianyi() {
            return pianyi;
        }

        public void setPianyi(int pianyi) {
            this.pianyi = pianyi;
        }

        public int getStates() {
            return states;
        }

        public void setStates(int states) {
            this.states = states;
        }

        public String getUgid() {
            return ugid;
        }

        public void setUgid(String ugid) {
            this.ugid = ugid;
        }

        public int getWcprice() {
            return wcprice;
        }

        public void setWcprice(int wcprice) {
            this.wcprice = wcprice;
        }
    }
}
