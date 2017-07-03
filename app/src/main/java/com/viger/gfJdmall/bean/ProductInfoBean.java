package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ProductInfoBean {


    /**
     * id : 1
     * imgUrls : ["/img/info/pp1.jpg","商品图片路径"]
     * price : 价格
     * ifSaleOneself : 是否自营
     * name : 商品名称
     * recomProductId : 推荐商品id
     * stockCount : 库存
     * commentCount : 评论数
     * typeList : ["麦片巧克力","商品版本"]
     * favcomRate : 好评率
     * recomProduct : 推荐商品标题
     */

    private int id;
    private String price;
    private boolean ifSaleOneself;
    private String name;
    private String recomProductId;
    private String stockCount;
    private String commentCount;
    private String favcomRate;
    private String recomProduct;
    private String imgUrls;
    private String typeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean getIfSaleOneself() {
        return ifSaleOneself;
    }

    public void setIfSaleOneself(boolean ifSaleOneself) {
        this.ifSaleOneself = ifSaleOneself;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecomProductId() {
        return recomProductId;
    }

    public void setRecomProductId(String recomProductId) {
        this.recomProductId = recomProductId;
    }

    public String getStockCount() {
        return stockCount;
    }

    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getFavcomRate() {
        return favcomRate;
    }

    public void setFavcomRate(String favcomRate) {
        this.favcomRate = favcomRate;
    }

    public String getRecomProduct() {
        return recomProduct;
    }

    public void setRecomProduct(String recomProduct) {
        this.recomProduct = recomProduct;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }
}
