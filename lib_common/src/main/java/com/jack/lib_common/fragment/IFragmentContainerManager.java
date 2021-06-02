package com.jack.lib_common.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * @fileName: IFragmentContainerManager
 * @author: susy
 * @date: 2021/5/28 18:18
 * @description: Fragment 容器管理接口
 */
public interface IFragmentContainerManager {
    /**
     * 替换fragment
     * @param fragment 要替换的 fragment
     * @param tag fragment 标签
     * @param isBackStack 是否要回滚
     */
    void replace(Fragment fragment, String tag, boolean isBackStack);

    /**
     * 添加fragment
     * @param fragment 要添加的 fragment
     * @param tag fragment 标签
     * @param isBackStack 是否要回滚
     */
    void add(Fragment fragment,String tag,boolean isBackStack);

    /**
     * 模拟按下返回键
     * @param name 返回到的Fragment的名字
     * @param flags 模式
     */
    void goBack(String name, int flags);

    /**
     * 通过tag获取到某个Fragment
     * @param tag 标签
     * @return tag对应的Fragment
     */
    Fragment getAllFragment(String tag);

    /**
     * 删除某个Fragment
     * @param fragment 实例
     */
    void remove(Fragment fragment);

    /**
     * 隐藏Fragment 没有删除view
     * @param fragment
     */
    void hide(Fragment fragment);

    /**
     * 显示Fragment
     * @param fragment
     */
    void show(Fragment fragment);

    /**
     * 获取所有的Fragment
     * @return
     */
    List getFragList();

    /**
     * 获取Fragment管理器
     * @return
     */
    FragmentManager getFragManager();

    /**
     * 设置容器的Id
     * @param containerId
     */
    void setContainerId(int containerId);
}
