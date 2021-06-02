package com.jack.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jack.biz_homepage.fragment.HomePageFragment;
import com.jack.lib_common.network.HttpUtil;
import com.jack.lib_common.network.IResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;
import org.robolectric.util.FragmentTestUtil;

/**
 * @fileName: RequestCheck
 * @author: susy
 * @date: 2021/6/1 21:20
 * @description: 网络请求测试
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MainTest {
    /**
     * 测试MainActivity启动
     */
    @Test
    public void testMainActivityLifecycle() throws Exception{
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = controller.get();
        Assert.assertNotNull (activity);
    }
    /**
     * 测试Fragment显示
     */
    @Test
    public void testHomepageFragment(){
        HomePageFragment homePageFragment = HomePageFragment.newInstance("", "");
        Assert.assertNotNull(homePageFragment.getView());
    }
    /**
     *  验证Get请求结果
     */
    @Test
    public void isGetResult(){
        HttpUtil.getInstance().init(RuntimeEnvironment.getApplication());

        HttpUtil.getInstance().doGet("https://itunes.apple.com/hk/rss/topgrossingapplications/limit=5/json", new IResponse() {
            @Override
            public void onResponse(String result) {
                Assert.assertNotNull(result);
            }

            @Override
            public void onFail(String error) {
                Assert.assertNotNull(error);
            }
        });
    }


}
