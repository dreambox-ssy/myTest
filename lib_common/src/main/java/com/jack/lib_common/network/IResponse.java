package com.jack.lib_common.network;

/**
 * @fileName: IResponse
 * @author: susy
 * @date: 2021/5/28 20:47
 * @description: 响应接口
 */
public interface IResponse {
    /**
     * 响应结果
     * @param result 结果数据
     */
    void onResponse(String result);

    /**
     * 响应失败
     * @param error 失败信息
     */
    void onFail(String error);
}
