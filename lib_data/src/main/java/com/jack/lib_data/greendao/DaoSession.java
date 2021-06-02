package com.jack.lib_data.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jack.lib_data.bean.FreeAppItem;

import com.jack.lib_data.greendao.FreeAppItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig freeAppItemDaoConfig;

    private final FreeAppItemDao freeAppItemDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        freeAppItemDaoConfig = daoConfigMap.get(FreeAppItemDao.class).clone();
        freeAppItemDaoConfig.initIdentityScope(type);

        freeAppItemDao = new FreeAppItemDao(freeAppItemDaoConfig, this);

        registerDao(FreeAppItem.class, freeAppItemDao);
    }
    
    public void clear() {
        freeAppItemDaoConfig.clearIdentityScope();
    }

    public FreeAppItemDao getFreeAppItemDao() {
        return freeAppItemDao;
    }

}
