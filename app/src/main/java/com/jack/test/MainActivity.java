package com.jack.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jack.biz_homepage.fragment.HomePageFragment;
import com.jack.lib_common.fragment.FragmentContainerManager;
import com.jack.lib_common.network.NetUtil;

/**
 * @fileName: MainActivity
 * @author: susy
 * @date: 2021/5/31 19:16
 * @description: 主Activity
 */
public class MainActivity extends AppCompatActivity{
    private final String TAG_HOME_PAGE = "HOME_PAGE";
    private FragmentContainerManager mFragmentContainerManager;
    private HomePageFragment mHomePageFragment = new HomePageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkNetwork();
    }

    private void init(){
        mFragmentContainerManager = new FragmentContainerManager(this, R.id.fl_container);
        mFragmentContainerManager.add(mHomePageFragment, TAG_HOME_PAGE, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragmentContainerManager.show(mHomePageFragment);
    }

    private void checkNetwork(){
        boolean isAvailable = NetUtil.isNetworkAvailable(this);
        if (isAvailable){

        }else {
            Toast.makeText(this, R.string.network_is_not_available, Toast.LENGTH_LONG).show();
        }
    }
}