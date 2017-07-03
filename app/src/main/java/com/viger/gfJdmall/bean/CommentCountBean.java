package com.viger.gfJdmall.bean;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CommentCountBean {


    /**
     * moderateCom : 中评数
     * allComment : 全部评论数
     * hasImgCom : 有图评论数
     * negativeCom : 差评数
     * positiveCom : 好评数
     */

    private String moderateCom;
    private String allComment;
    private String hasImgCom;
    private String negativeCom;
    private String positiveCom;

    public String getModerateCom() {
        return moderateCom;
    }

    public void setModerateCom(String moderateCom) {
        this.moderateCom = moderateCom;
    }

    public String getAllComment() {
        return allComment;
    }

    public void setAllComment(String allComment) {
        this.allComment = allComment;
    }

    public String getHasImgCom() {
        return hasImgCom;
    }

    public void setHasImgCom(String hasImgCom) {
        this.hasImgCom = hasImgCom;
    }

    public String getNegativeCom() {
        return negativeCom;
    }

    public void setNegativeCom(String negativeCom) {
        this.negativeCom = negativeCom;
    }

    public String getPositiveCom() {
        return positiveCom;
    }

    public void setPositiveCom(String positiveCom) {
        this.positiveCom = positiveCom;
    }
}
