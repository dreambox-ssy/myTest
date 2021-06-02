package com.jack.biz_homepage.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.biz_homepage.bean.DetailItem;
import com.jack.biz_homepage.bean.FreeItem;
import com.jack.biz_homepage.bean.RecommendItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName: JsonParser
 * @author: susy
 * @date: 2021/5/31 12:23
 * @description: Json 协议解析
 */
public class JsonParser {
    public static List<RecommendItem> parseRecommendData(String json){
        List<RecommendItem> lsData = new ArrayList<>();
        try {
            JSONObject data = JSON.parseObject(json);
            JSONObject feed = data.getJSONObject("feed");
            JSONArray entry = feed.getJSONArray("entry");
            for (int i = 0; i < entry.size(); i ++){
                RecommendItem recommendItem = new RecommendItem();
                JSONObject item = entry.getJSONObject(i);
                JSONArray arrayImage = item.getJSONArray("im:image");
                JSONObject category = item.getJSONObject("category");
                JSONObject name = item.getJSONObject("im:name");
                JSONObject objSummary = item.getJSONObject("summary");
                for (int j = 0; j < arrayImage.size(); j ++){
                    JSONObject objImage = arrayImage.getJSONObject(j);
                    JSONObject objImageAttribute = objImage.getJSONObject("attributes");
                    String imageHeight = objImageAttribute.getString("height");
                    if (TextUtils.equals(imageHeight, "100")){
                        String imageLabel = objImage.getString("label");
                        recommendItem.setIconUrl(imageLabel);
                        break;
                    }
                }
                JSONObject objCategoryAttr = category.getJSONObject("attributes");
                String categoryLabel = objCategoryAttr.getString("label");
                recommendItem.setCategory(categoryLabel);
                String nameLabel = name.getString("label");
                recommendItem.setName(nameLabel);
                String summaryLabel = objSummary.getString("label");
                recommendItem.setSummary(summaryLabel);
                lsData.add(recommendItem);
            }
        } catch (Exception e){
            e.toString();
        }
        return lsData;
    }

    public static List<FreeItem> parseFreeData(String json){
        List<FreeItem> lsData = new ArrayList<>();
        try {
            JSONObject data = JSON.parseObject(json);
            JSONObject feed = data.getJSONObject("feed");
            JSONArray entry = feed.getJSONArray("entry");
            for (int i = 0; i < entry.size(); i ++){
                FreeItem freeItem = new FreeItem();
                JSONObject item = entry.getJSONObject(i);
                JSONArray arrayImage = item.getJSONArray("im:image");
                JSONObject category = item.getJSONObject("category");
                JSONObject name = item.getJSONObject("im:name");
                JSONObject objSummary = item.getJSONObject("summary");
                JSONObject objId = item.getJSONObject("id");
                for (int j = 0; j < arrayImage.size(); j ++){
                    JSONObject objImage = arrayImage.getJSONObject(j);
                    JSONObject objImageAttribute = objImage.getJSONObject("attributes");
                    String imageHeight = objImageAttribute.getString("height");
                    if (TextUtils.equals(imageHeight, "100")){
                        String imageLabel = objImage.getString("label");
                        freeItem.setIconUrl(imageLabel);
                        break;
                    }
                }
                JSONObject objCategoryAttr = category.getJSONObject("attributes");
                String categoryLabel = objCategoryAttr.getString("label");
                freeItem.setCategory(categoryLabel);
                String nameLabel = name.getString("label");
                freeItem.setName(nameLabel);
                String summaryLabel = objSummary.getString("label");
                freeItem.setSummary(summaryLabel);
                JSONObject objIdAttr = objId.getJSONObject("attributes");
                String id = objIdAttr.getString("im:id");
                freeItem.setId(id);
                JSONObject objArtist = item.getJSONObject("im:artist");
                String artist = objArtist.getString("label");
                freeItem.setArtist(artist);
                lsData.add(freeItem);
            }
        } catch (Exception e){
            e.toString();
        }
        return lsData;
    }

    public static DetailItem parseDetail(String json){
        DetailItem detailItem = new DetailItem();
        try {
            JSONObject data = JSON.parseObject(json);
            JSONArray result = data.getJSONArray("results");
            for (int i = 0; i < result.size(); i ++){
                JSONObject item = result.getJSONObject(i);
                String trackId = item.getString("trackId");
                String trackContentRating = item.getString("trackContentRating");
                int userRatingCount = item.getIntValue("userRatingCount");
                detailItem.setId(trackId);
                detailItem.setRating(trackContentRating);
                detailItem.setRatingCount(userRatingCount);
            }
        } catch (Exception e){
            e.toString();
        }
        return detailItem;
    }
}
