package com.radish.baselibrary.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.radish.baselibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/19 10:01 AM/
 * @邮箱 15703379121@163.com
 * @描述
 */
public class DaoSupport<T> implements IDaoSupport<T> {
    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;
    @SuppressLint("NewApi")
    private ArrayMap<String, Method> mPutMethods = new ArrayMap<>();

    private static final Object[] mPutMethodArgs = new Object[2];
    private QuerySupport<T> mQuerySupport;

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
        //创建表
        StringBuffer sql = new StringBuffer();
        sql.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append(" (id integer primary key autoincrement, ");
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if ("id".equals(name))
                continue;
            String type = field.getType().getSimpleName();
            // type 需要进行转换 int 转为 integer , String 转为 text
            sql.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }
        sql.replace(sql.length() - 2, sql.length(), ")");
        mSqLiteDatabase.execSQL(sql.toString());
    }

    // 插入数据库
    @Override
    public Long insert(T obj) {
        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    @Override
    public void insert(List<T> list) {
        // 批量插入采用事务（优化）
        mSqLiteDatabase.beginTransaction();
        for (T t : list) {
            insert(t);
        }
        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
    }

    @Override
    public long delete(String whereClause, String... whereArgs) {
        return mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }

    @Override
    public long update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz), values, whereClause, whereArgs);
    }

    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport == null) {
            synchronized (DaoSupport.class) {
                if (mQuerySupport == null) {
                    mQuerySupport = new QuerySupport<T>(mSqLiteDatabase, mClazz);
                }
            }
        }
        return mQuerySupport;
    }

    private ContentValues contentValuesByObj(T obj) {
        ContentValues values = new ContentValues();
        try {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);

                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;
                String fileTypeName = field.getType().getName();
                Method putMethod = mPutMethods.get(fileTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fileTypeName, putMethod);
                }
                //通过反射执行
                putMethod.invoke(values, mPutMethodArgs);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            mPutMethodArgs[0] = null;
            mPutMethodArgs[1] = null;
        }

        return values;
    }
}
