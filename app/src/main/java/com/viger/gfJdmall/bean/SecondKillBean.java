package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class SecondKillBean {

    /**
     * total : 秒杀商品总数
     * rows : [{"allPrice":"原价","pointPrice":"秒杀价格","iconUrl":"商品图片路径","timeLeft":"秒杀剩余时间（分钟）","type":"秒杀类型（1抢年货，2超值，3热卖）","productId":"商品id"}]
     */

    private String total;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * allPrice : 原价
         * pointPrice : 秒杀价格
         * iconUrl : 商品图片路径
         * timeLeft : 秒杀剩余时间（分钟）
         * type : 秒杀类型（1抢年货，2超值，3热卖）
         * productId : 商品id
         */

        private String allPrice;
        private String pointPrice;
        private String iconUrl;
        private String timeLeft;
        private String type;
        private String productId;

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

        public String getPointPrice() {
            return pointPrice;
        }

        public void setPointPrice(String pointPrice) {
            this.pointPrice = pointPrice;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getTimeLeft() {
            return timeLeft;
        }

        public void setTimeLeft(String timeLeft) {
            this.timeLeft = timeLeft;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}
