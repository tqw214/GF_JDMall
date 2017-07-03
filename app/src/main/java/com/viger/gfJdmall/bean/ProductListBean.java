package com.viger.gfJdmall.bean;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ProductListBean {


    /**
     * id : 商品id
     * price : 商品价格
     * name : 商品名称
     * iconUrl : 商品图片
     * commentCount : 评论数
     * favcomRate : 好评率
     */

    private long id;
    private double price;
    private String name;
    private String iconUrl;
    private int commentCount;
    private int favcomRate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavcomRate() {
        return favcomRate;
    }

    public void setFavcomRate(int favcomRate) {
        this.favcomRate = favcomRate;
    }
}
