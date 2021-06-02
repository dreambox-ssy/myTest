package com.jack.biz_homepage.utils;

import com.jack.biz_homepage.bean.FreeItem;
import com.jack.lib_data.bean.FreeAppItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName: DatabaseBeanConvertor
 * @author: susy
 * @date: 2021/6/1 20:36
 * @description: 数据库数据结构转换
 */
public class DatabaseConvertor {
    public static FreeAppItem freeItem2FreeAppItem(FreeItem item){
        FreeAppItem freeAppItem = new FreeAppItem();
        if (item != null){
            freeAppItem.setAppId(item.getId());
            freeAppItem.setArtist(item.getArtist());
            freeAppItem.setCategory(item.getCategory());
            freeAppItem.setDownloadCount(item.getDownloadCount());
            freeAppItem.setIconUrl(item.getIconUrl());
            freeAppItem.setName(item.getName());
            freeAppItem.setSummary(item.getSummary());
            freeAppItem.setRate(item.getRate());
        }
        return freeAppItem;
    }

    public static List<FreeAppItem> freeItems2FreeAppItems(List<FreeItem> items){
        List<FreeAppItem> freeAppItems = new ArrayList<>();
        if (items != null){
            for (FreeItem item : items){
                freeAppItems.add(freeItem2FreeAppItem(item));
            }
        }
        return freeAppItems;
    }

    public static FreeItem freeAppItem2FreeItem(FreeAppItem item){
        FreeItem freeItem = new FreeItem();
        if (item != null){
            freeItem.setId(item.getAppId());
            freeItem.setArtist(item.getArtist());
            freeItem.setCategory(item.getCategory());
            freeItem.setDownloadCount(item.getDownloadCount());
            freeItem.setIconUrl(item.getIconUrl());
            freeItem.setName(item.getName());
            freeItem.setSummary(item.getSummary());
            freeItem.setRate(item.getRate());
        }
        return freeItem;
    }

    public static List<FreeItem> freeAppItems2FreeItems(List<FreeAppItem> items){
        List<FreeItem> freeAppItems = new ArrayList<>();
        if (items != null){
            for (FreeAppItem item : items){
                freeAppItems.add(freeAppItem2FreeItem(item));
            }
        }
        return freeAppItems;
    }
}
