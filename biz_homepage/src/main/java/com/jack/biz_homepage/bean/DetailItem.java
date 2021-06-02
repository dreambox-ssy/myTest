package com.jack.biz_homepage.bean;

/**
 * @fileName: DetailItem
 * @author: susy
 * @date: 2021/5/31 19:34
 * @description: 根据ID请求返回的详细数据内容
 */
public class DetailItem {
    private String id;
    private String rating;
    private int ratingCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
