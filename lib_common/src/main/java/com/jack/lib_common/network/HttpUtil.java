package com.jack.lib_common.network;

import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @fileName: HttpUtil
 * @author: susy
 * @date: 2021/5/28 18:06
 * @description: Http连接工具封装
 */
public class HttpUtil implements IRequest{
    private final static int CACHE_SIZE = 10 * 1024 * 1024;
    private static OkHttpClient mClient;
    private Context mContext;

    /**
     * Retrofit
     */
    private static Retrofit retrofit;

    public static Handler mHandler = new Handler();

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    private volatile static HttpUtil instance = null;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (null == instance) {
            synchronized (HttpUtil.class) {
                if (null == instance) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mClient = getClient();
    }

    /**
     * 获取Retrofit的实例
     *
     * @return retrofit
     */
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/hk/rss/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getClient() {
        if (mClient == null) {
            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder().
                    connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .cache(new Cache(mContext.getExternalFilesDir("manbaOkhttp"), CACHE_SIZE));
            mClient = mBuilder.build();
        }
        return mClient;

    }

    @Override
    public void doGet(String url, IResponse callback) {
        doGet(url, null, null, callback);
    }

    @Override
    public void doGet(String url, Map<String, String> paramsMap, IResponse callback) {
        doGet(url, paramsMap, null, callback);
    }

    @Override
    public void doGet(String url, Map<String, String> paramsMap, NetworkOption option, final IResponse callback) {
        url = NetUtil.appendUrl(url, paramsMap);
        final NetworkOption manbaOkhttpOption = NetUtil.checkNetworkOption(option, url);
        Request.Builder builder = new Request.Builder().url(url).tag(manbaOkhttpOption.getTag());
        builder = configHeaders(builder, manbaOkhttpOption);

        Request build = builder.build();

        getClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleError(e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResult(response, callback);
            }
        });
    }

    @Override
    public void doPost(String url, Map<String, String> paramsMap, IResponse callback) {
        doPost(url,paramsMap,null,callback);
    }

    @Override
    public void doPost(String url, Map<String, String> paramsMap, NetworkOption option, final IResponse callback) {
        url = NetUtil.appendUrl(url, paramsMap);
        final NetworkOption okhttpOption = NetUtil.checkNetworkOption(option, url);
        // 以表单的形式提交
        FormBody.Builder builder = new FormBody.Builder();
        builder=configPostParam(builder,paramsMap);
        FormBody formBody = builder.build();

        Request.Builder requestBuilder = new Request.Builder().url(url).post(formBody).tag(okhttpOption.getTag());
        requestBuilder = configHeaders(requestBuilder, okhttpOption);

        Request build = requestBuilder.build();

        getClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleError(e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResult(response, callback);
            }
        });
    }

    private FormBody.Builder configPostParam(FormBody.Builder builder, Map<String, String> paramsMap) {
        if(paramsMap!=null){
            Set<Map.Entry<String, String>> entries = paramsMap.entrySet();
            for(Map.Entry<String,String> entry:entries ){
                String key = entry.getKey();
                String value = entry.getValue();
                builder.add(key,value);
            }
        }
        return builder;
    }

    private Request.Builder configHeaders(Request.Builder builder, NetworkOption option) {
        Map<String, String> headers = option.getHeaders();
        if (headers == null || headers.size() == 0) {
            return builder;
        }
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.addHeader(key, value);
        }
        return builder;

    }


    private void handleResult(Response response, final IResponse callback) throws IOException {
        final String result = response.body().string();
        if (callback != null) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(result);
                }
            });
        }
    }

    private void handleError(IOException e, final IResponse callback) {
        if (callback != null) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onFail(e.toString());
                }
            });

        }
    }

    @Override
    public void cancel(String tag) {
        if (mClient != null) {
            for (Call call : mClient.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(tag)) {
                    call.cancel();
                }
            }
        }
    }
}
