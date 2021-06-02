package com.jack.biz_homepage.intf;

/**
 * @fileName: IHomePagePresenter
 * @author: susy
 * @date: 2021/5/27 19:25
 * @description: 主页Presenter接口
 */
public interface IHomePagePresenter {
    /**
     * 加载推荐应用数据
     */
    void loadRecommendApplicationData();

    /**
     * 加载免费应用数据
     */
    void loadFreeApplicationData();

    /**
     * 加载应用详细数据
     * @param id 应用的ID
     */
    void loadDetailById(String id);

    /**
     * 搜索本地应用数据
     * @param keyword 搜索的关键字，支持应⽤名、开发者名和应用描述
     */
    void searchLocalApplicationData(String keyword);

    /**
     * 加载下一页
     * 该函数只负责免费应用数据
     */
    void nextPage();
}
