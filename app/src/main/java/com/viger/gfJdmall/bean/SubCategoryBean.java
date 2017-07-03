package com.viger.gfJdmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class SubCategoryBean{


    /**
     * id : 13
     * name : 裙装
     * thirdCategory : [{"bannerUrl":"/img/lyq.jpg","id":7,"name":"连衣裙"}]
     */

    private int id;
    private String name;
    private List<ThirdCategoryBean> thirdCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ThirdCategoryBean> getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(List<ThirdCategoryBean> thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

    public static class ThirdCategoryBean{
        /**
         * bannerUrl : /img/lyq.jpg
         * id : 7
         * name : 连衣裙
         */

        private String bannerUrl;
        private int id;
        private String name;

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
