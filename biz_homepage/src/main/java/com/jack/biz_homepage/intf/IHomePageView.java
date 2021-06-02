package com.jack.biz_homepage.intf;

import com.jack.biz_homepage.bean.DetailItem;
import com.jack.biz_homepage.bean.FreeItem;
import com.jack.biz_homepage.bean.RecommendItem;

import java.util.List;

/**
 * @fileName: IHomePageView
 * @author: susy
 * @date: 2021/5/27 17:25
 * @description: 主页View接口
 */
public interface IHomePageView {
    /**
     * 更新推荐应用列表
     * @param lsRecommendApplication 推荐应用数据
     */
    void onUpdateRecommendApplicationData(List<RecommendItem> lsRecommendApplication);

    /**
     * 更新免费应用列表
     * @param lsFreeApplication 免费应用数据
     */
    void onUpdateFreeApplicationData(List<FreeItem> lsFreeApplication);

    /**
     * 更新免费应用星级及星级数量
     * @param detailItem 免费应用的详情信息
     */
    void onUpdateFreeApplicationDetail(DetailItem detailItem);

    /**
     * 更新搜索结果
     * @param lsFreeApplication 搜索结果
     */
    void onUpdateSearchResult(List<FreeItem> lsFreeApplication);

    /**
     * 显示搜索结果
     * @param bShow true:show,false:hide
     */
    void onShowSearchResult(boolean bShow);
    /**
     * 加载失败
     * @param info 失败信息
     */
    void onError(String info);
}
