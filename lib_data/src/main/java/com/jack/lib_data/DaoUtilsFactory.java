package com.jack.lib_data;

import com.jack.lib_data.bean.FreeAppItem;
import com.jack.lib_data.greendao.FreeAppItemDao;

/**
 * @fileName: DaoUtilsFactory
 * @author: susy
 * @date: 2021/6/1 19:19
 * @description: 初始化、存放及获取DaoUtils
 */
public class DaoUtilsFactory {
    private volatile static DaoUtilsFactory instance = new DaoUtilsFactory();
    private CommonDaoUtils<FreeAppItem> mFreeAppDaoUtils;

    public static DaoUtilsFactory getInstance() {
        return instance;
    }

    public CommonDaoUtils<FreeAppItem> getFreeApplicationDaoUtil(){
        if (mFreeAppDaoUtils == null){
            FreeAppItemDao freeAppItemDao = DaoManager.getInstance().getDaoSession().getFreeAppItemDao();
            mFreeAppDaoUtils = new CommonDaoUtils<>(FreeAppItem.class, freeAppItemDao);
        }
        return mFreeAppDaoUtils;
    }
}
