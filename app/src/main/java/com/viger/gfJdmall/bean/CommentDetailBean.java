package com.viger.gfJdmall.bean;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CommentDetailBean {


    /**
     * id : 0
     * imgUrls :
     * rate : 1
     * loveCount : 喜欢数
     * commentTime : 2016-03-06 21:55:40
     * buyTime : 2016-03-02 11:12:19
     * userLevel : 2
     * subComment : 1
     * userName : 2
     * comment : 评论内容
     * userImg : 用户头像路径
     * productType : 产品版本类型
     */

    private long id;
    private String imgUrls;
    private int rate;
    private int loveCount;
    private String commentTime;
    private String buyTime;
    private int userLevel;
    private int subComment;
    private String userName;
    private String comment;
    private String userImg;
    private String productType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getSubComment() {
        return subComment;
    }

    public void setSubComment(int subComment) {
        this.subComment = subComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
