package com.jack.lib_data;

import com.jack.lib_data.greendao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @fileName: CommonDaoUtils
 * @author: susy
 * @date: 2021/6/1 19:18
 * @description: 数据表的操作:增、删、改、查
 */
public class CommonDaoUtils<T> {
    private DaoSession mDaoSession;
    private Class<T> mEntityClass;
    private AbstractDao<T, Long> mEntityDao;

    public CommonDaoUtils(Class<T> pEntityClass, AbstractDao<T, Long> pEntityDao) {
        DaoManager mManager = DaoManager.getInstance();
        mDaoSession = mManager.getDaoSession();
        mEntityClass = pEntityClass;
        mEntityDao = pEntityDao;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     * @param pEntity
     * @return true:成功,false:失败
     */
    public boolean insert(T pEntity) {
        return mEntityDao.insert(pEntity) != -1;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param pEntityList
     * @return true:成功,false:失败
     */
    public boolean insertMultiple(final List<T> pEntityList) {
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T entity : pEntityList) {
                        mDaoSession.insertOrReplace(entity);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改一条数据
     * @param entity
     * @return true:成功,false:失败
     */
    public boolean update(T entity) {
        try {
            mDaoSession.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单条记录
     * @param entity
     * @return true:成功,false:失败
     */
    public boolean delete(T entity) {
        try {
            //按照id删除
            mDaoSession.delete(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有记录
     * @return true:成功,false:失败
     */
    public boolean deleteAll() {
        try {
            //按照id删除
            mDaoSession.deleteAll(mEntityClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<T> queryAll() {
        return mDaoSession.loadAll(mEntityClass);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public T queryById(long key) {
        return mDaoSession.load(mEntityClass, key);
    }

    /**
     * 使用native sql进行查询操作
     * @param sql
     * @param conditions
     * @return
     */
    public List<T> queryByNativeSql(String sql, String[] conditions) {
        return mDaoSession.queryRaw(mEntityClass, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @param cond
     * @param condMore
     * @return
     */
    public List<T> queryByQueryBuilder(WhereCondition cond, WhereCondition... condMore) {
        QueryBuilder<T> queryBuilder = mDaoSession.queryBuilder(mEntityClass);
        return queryBuilder.where(cond, condMore).build().list();
    }

    /**
     * 获取QueryBuilder
     * @return
     */
    public QueryBuilder<T> getQueryBuilder(){
        return mDaoSession.queryBuilder(mEntityClass);
    }
}
