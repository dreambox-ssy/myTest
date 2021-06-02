package com.jack.lib_common.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @fileName: LoggingInterceptor
 * @author: susy
 * @date: 2021/5/28 21:12
 * @description:
 */
public class LoggingInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return response;
    }
}
