package com.radish.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * @作者 radish
 * @创建日期 2018/12/19 10:01 AM
 * @邮箱 15703379121@163.com
 * @描述 数据库工厂，并且单例模式
 */
public class DaoSupportFactory {
    private static DaoSupportFactory mFactory;

    //持有外部数据库的引用
    private final SQLiteDatabase mSqLiteDatabase;

    private DaoSupportFactory(String dbPath, String dbName) {
        //把数据库放到内存卡里
        File dbRoot = new File(dbPath);
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File db = new File(dbPath, dbName);
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(db, null);
    }

    public static DaoSupportFactory getFactory(String dbPath, String dbName) {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory(dbPath, dbName);
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport<T> ();
        daoSupport.init(mSqLiteDatabase,clazz);
        return daoSupport;
    }
}
