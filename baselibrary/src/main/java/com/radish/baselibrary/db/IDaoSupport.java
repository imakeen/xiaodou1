package com.radish.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/19 9:59 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public interface IDaoSupport<T> {
    //初始化
    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    //插入
    Long insert(T t);

    //批量插入
    void insert(List<T> list);

    //删除
    long delete(String whereClause, String... whereArgs);

    //更新
    long update(T obj, String whereClause, String... whereArgs);

    //获取查询支持类
    QuerySupport<T> querySupport();
}
