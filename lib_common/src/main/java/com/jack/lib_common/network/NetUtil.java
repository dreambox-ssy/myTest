package com.jack.lib_common.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.app.DownloadManager.Request.NETWORK_MOBILE;

/**
 * @fileName: NetUtil
 * @author: susy
 * @date: 2021/5/28 21:06
 * @description: 网络工具
 */
public class NetUtil {
    private static final String TAG = "NetUtils";
    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    public static Map<String, String> getCommonParams() {

        final Map<String, String> pairs = new HashMap<String, String>();

        return pairs;
    }

    @NonNull
    public static String checkUrl(String url) {
        if(!url.startsWith("http:") && !url.startsWith("https:")){

        }
        return url;
    }

    public static NetworkOption checkNetworkOption(NetworkOption networkOption, String url) {
        if (networkOption == null) {
            networkOption = new NetworkOption.Builder().setTag(url).build();
        }
        return networkOption;
    }

    public static boolean needLoginAgain(int code) {
        return code == 10112 || code == 10113;
    }

    public static boolean isSuccessful(int code) {
        return code == 0;
    }

    public static String appendUrl(String origin, Map<String, String> nameValuePairs) {
        if (origin == null) {
            return null;
        }
        if(nameValuePairs==null){
            return origin;
        }
        try {
            boolean first = true;
            StringBuilder urlBuilder = new StringBuilder(origin);
            if (!origin.contains("?")) {
                urlBuilder.append("?");
            } else {
                urlBuilder.append("&");
                first = false;
            }
            for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
                if (!first) {
                    urlBuilder.append("&");
                } else {
                    first = false;
                }
                String name = entry.getKey();
                String value = entry.getValue();
                Log.i(TAG, "name: " + name + ", value: " + value);
                urlBuilder.append(name);
                urlBuilder.append("=");
                if (value.contains(" ")) {
                    urlBuilder.append(Uri.encode(value, null));
                } else {
                    urlBuilder.append(URLEncoder.encode(value, "UTF-8"));
                }
            }
            Log.i(TAG, "after append get params, new url is: " + urlBuilder.toString());
            return urlBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return origin;
    }

    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT >= 23) {
            //获取网络属性
            NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }
        }else {
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
