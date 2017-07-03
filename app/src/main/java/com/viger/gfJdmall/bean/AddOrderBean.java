package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class AddOrderBean {

        private List<Product> products;
        private long addrId;
        private int payWay;
        private long userId;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public long getAddrId() {
            return addrId;
        }

        public void setAddrId(long addrId) {
            this.addrId = addrId;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public static class Product {

            private int buyCount;
            private String type;
            private long pid;

            public long getPid() {
                return pid;
            }

            public void setPid(long pid) {
                this.pid = pid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(int buyCount) {
                this.buyCount = buyCount;
            }

        }

}
