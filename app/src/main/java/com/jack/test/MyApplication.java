package com.jack.test;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.jack.lib_data.DaoManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * @fileName: MyApplication
 * @author: susy
 * @date: 2021/5/31 20:16
 * @description:
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        context = getApplicationContext();
        initDB();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化数据库
     */
    private void initDB(){
        DaoManager.getInstance().init(this);
    }
}
