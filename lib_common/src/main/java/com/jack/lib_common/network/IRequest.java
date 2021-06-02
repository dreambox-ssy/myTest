package com.jack.lib_common.network;

import android.content.Context;

import java.util.Map;

/**
 * @fileName: IRequest
 * @author: susy
 * @date: 2021/5/28 18:29
 * @description: 请求接口
 */
public interface IRequest {
    /**
     * 初始化
     * @param context
     */
    void init(Context context);

    /**
     * Get 请求
     * @param url
     * @param callback
     */
    void doGet(String url, IResponse callback);

    /**
     * Get 请求
     * @param url
     * @param paramsMap
     * @param callback
     */
    void doGet(String url, Map<String, String> paramsMap, IResponse callback);

    /**
     * Get 请求
     * @param url
     * @param paramsMap
     * @param option
     * @param callback
     */
    void doGet(String url, Map<String, String> paramsMap, NetworkOption option, IResponse callback);

    /**
     * Post 请求
     * @param url
     * @param paramsMap
     * @param callback
     */
    void doPost(String url, Map<String, String> paramsMap, IResponse callback);

    /**
     * Post 请求
     * @param url
     * @param paramsMap
     * @param option
     * @param callback
     */
    void doPost(String url, Map<String, String> paramsMap, NetworkOption option, IResponse callback);

    /**
     * 取消请求
     * @param tag
     */
    void cancel(String tag);
}
