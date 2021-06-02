package com.jack.lib_data;

import com.jack.lib_data.bean.FreeAppItem;
import com.jack.lib_data.greendao.FreeAppItemDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @fileName: FreeAppDatabaseUtil
 * @author: susy
 * @date: 2021/6/1 19:51
 * @description: free application database controller
 */
public class FreeAppDatabaseUtil {
    /**
     * 增加一条数据
     * @param item {@link FreeAppItem}
     * @return true:成功,false:失败
     */
    public static boolean add(FreeAppItem item){
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().insert(item);
    }

    /**
     * 批量增加数据
     * @param lsItem {@link FreeAppItem}
     * @return true:成功,false:失败
     */
    public static boolean addMultiple(List<FreeAppItem> lsItem){
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().insertMultiple(lsItem);
    }

    /**
     * 删除一条数据
     * @param item {@link FreeAppItem}
     * @return true:成功,false:失败
     */
    public static boolean del(FreeAppItem item){
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().delete(item);
    }

    /**
     * 更新数据
     * @param item {@link FreeAppItem}
     * @return true:成功,false:失败
     */
    public static boolean update(FreeAppItem item){
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().update(item);
    }



    public static List<FreeAppItem> queryAll() {
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().queryAll();
    }

    public static List<FreeAppItem> queryByKeyword(String keyword) {
        String sqlKeyword = "%" + keyword + "%";
        WhereCondition whereName = FreeAppItemDao.Properties.Name.like(sqlKeyword);
        WhereCondition whereArtist = FreeAppItemDao.Properties.Artist.like(sqlKeyword);
        WhereCondition whereSummary = FreeAppItemDao.Properties.Summary.like(sqlKeyword);
        //return DaoUtilsStore.getInstance().getFreeApplicationDaoUtil().queryByKeyword(keyword);
        return DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().queryByQueryBuilder(whereName, whereArtist, whereSummary);
    }

    public static List<FreeAppItem> queryKeyword(String keyword){
        String sqlKeyword = "%" + keyword + "%";
        QueryBuilder<FreeAppItem> qb = DaoUtilsFactory.getInstance().getFreeApplicationDaoUtil().getQueryBuilder();
        return qb
                .whereOr(
                        FreeAppItemDao.Properties.Name.like(sqlKeyword),
                        FreeAppItemDao.Properties.Artist.like(sqlKeyword),
                        FreeAppItemDao.Properties.Summary.like(sqlKeyword))
                .build()
                .list();
    }
}
