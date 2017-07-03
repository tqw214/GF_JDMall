package com.viger.gfJdmall.bean;

/**
 * Created by Administrator on 2017/5/17.
 */

public class RecommendBean {
    /**
     * price : 商品价格
     * name : 商品名称
     * iconUrl : 商品图片
     * productId : 商品id
     */

    private double price;
    private String name;
    private String iconUrl;
    private long productId;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
