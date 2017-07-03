package com.viger.gfJdmall.bean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ProductSortBean {

    private String keyword;
    private int categoryId;
    private int filterType = 1;
    private int sortType;
    private int deliverChoose;
    private int minPrice;
    private int maxPrice;
    private long brandId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getDeliverChoose() {
        return deliverChoose;
    }

    public void setDeliverChoose(int deliverChoose) {
        this.deliverChoose = deliverChoose;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }
}
