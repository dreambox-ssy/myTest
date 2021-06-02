package com.jack.biz_homepage.bean;

/**
 * @fileName: RecommendItem
 * @author: susy
 * @date: 2021/5/27 22:05
 * @description: 推荐 ITEM
 */
public class RecommendItem {
    private String iconUrl;
    private String name;
    private String category;
    private String summary;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
