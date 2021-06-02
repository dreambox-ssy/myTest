package com.jack.lib_data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @fileName: FreeAppItem
 * @author: susy
 * @date: 2021/6/1 22:20
 * @description: 免费应用数据库存储的数据结构
 */
@Entity
public class FreeAppItem {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String appId;

    @Property
    private String iconUrl;
    private String name;
    private String category;
    private String summary;
    private String artist;
    private int rate;
    private int downloadCount;
    @Generated(hash = 1850321837)
    public FreeAppItem(Long id, String appId, String iconUrl, String name,
            String category, String summary, String artist, int rate,
            int downloadCount) {
        this.id = id;
        this.appId = appId;
        this.iconUrl = iconUrl;
        this.name = name;
        this.category = category;
        this.summary = summary;
        this.artist = artist;
        this.rate = rate;
        this.downloadCount = downloadCount;
    }
    @Generated(hash = 1249592630)
    public FreeAppItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAppId() {
        return this.appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getIconUrl() {
        return this.iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getRate() {
        return this.rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public int getDownloadCount() {
        return this.downloadCount;
    }
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
}
