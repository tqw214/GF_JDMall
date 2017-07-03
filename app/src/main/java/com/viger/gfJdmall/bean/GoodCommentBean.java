package com.viger.gfJdmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class GoodCommentBean {


    /**
     * imgUrls : ["/img/p2.jpg","评论图片路径"]
     * time : 2016-03-06 21:36:10
     * rate :
     * userName :
     * type :
     * comment :
     */

    private String time;
    private int rate;
    private String userName;
    private int type;
    private String comment;
    private String imgUrls;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }
}
