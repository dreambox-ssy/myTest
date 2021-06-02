package com.jack.lib_common.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName: FragmentContainerManager
 * @author: susy
 * @date: 2021/5/28 18:58
 * @description: Fragment 容器管理
 */
public class FragmentContainerManager implements IFragmentContainerManager {
    /**
     * AppCompatActivity 实例
     */
    private AppCompatActivity mContext;

    /**
     * Fragment 管理器
     */
    private FragmentManager mFragmentManager;

    /**
     * 装Fragment的容器
     */
    private int mContainerId;

    /**
     * 该Activity所有fragment的集合
     */
    private List mListFragment;

    /**
     * @param context AppCompatActivity 实例
     * @param containerId  容器Id
     */
    public FragmentContainerManager(AppCompatActivity context, int containerId) {
        super();
        this.mContext = context;
        this.mContainerId = containerId;
        mFragmentManager = this.mContext.getSupportFragmentManager();
        mListFragment = new ArrayList();
    }

    @Override
    public void replace(Fragment fragment, String tag, boolean isBackStack) {
        mListFragment.add(fragment);
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        fTransaction.replace(mContainerId, fragment, tag);
        if (isBackStack) {
            fTransaction.addToBackStack(tag);
        }
        fTransaction.commit();
    }

    @Override
    public void add(Fragment fragment, String tag, boolean isBackStack) {
        mListFragment.add(fragment);
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        fTransaction.add(mContainerId, fragment, tag);
        if (isBackStack) {
            fTransaction.addToBackStack(tag);
        }
        fTransaction.commit();
    }

    @Override
    public void goBack(String name, int flags) {
        mFragmentManager.popBackStack(name, flags);
    }

    @Override
    public Fragment getAllFragment(String tag) {
        return (Fragment) mFragmentManager.findFragmentByTag(tag);
    }

    @Override
    public void remove(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    @Override
    public void hide(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }

    @Override
    public void show(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
        }
    }

    @Override
    public List getFragList() {
        return mListFragment;
    }

    @Override
    public FragmentManager getFragManager() {
        return mFragmentManager;
    }

    @Override
    public void setContainerId(int containerId) {
        this.mContainerId = containerId;
    }
}
