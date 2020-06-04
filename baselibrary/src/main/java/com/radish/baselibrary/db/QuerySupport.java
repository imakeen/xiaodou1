package com.radish.baselibrary.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.radish.baselibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/26 9:52 AM
 * @邮箱 15703379121@163.com
 * @描述 数据库查询支持类
 */
public class QuerySupport<T> {
    // 查询的列
    private String[] mQueryColumns;
    // 查询的条件
    private String mQuerySelection;
    // 查询的参数
    private String[] mQuerySelectionArgs;
    // 查询的分组
    private String mQueryGroupBy;
    // 查询对结果集进行过滤
    private String mQueryHaving;
    // 查询排序
    private String mQueryOrderBy;
    // 查询可用于分页
    private String mQueryLimit;

    private Class<T> mClazz;
    private SQLiteDatabase mSQLiteDatabase;

    public QuerySupport(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mClazz = clazz;
        this.mSQLiteDatabase = sqLiteDatabase;
    }

    public QuerySupport columns(String... columns) {
        this.mQueryColumns = columns;
        return this;
    }

    public QuerySupport selectionArgs(String... selectionArgs) {
        this.mQuerySelectionArgs = selectionArgs;
        return this;
    }

    public QuerySupport having(String having) {
        this.mQueryHaving = having;
        return this;
    }

    public QuerySupport orderBy(String orderBy) {
        this.mQueryOrderBy = orderBy;
        return this;
    }

    public QuerySupport limit(String limit) {
        this.mQueryLimit = limit;
        return this;
    }

    public QuerySupport groupBy(String groupBy) {
        this.mQueryGroupBy = groupBy;
        return this;
    }

    public QuerySupport selection(String selection) {
        this.mQuerySelection = selection;
        return this;
    }

    public List<T> query() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClazz), mQueryColumns, mQuerySelection,
                mQuerySelectionArgs, mQueryGroupBy, mQueryHaving, mQueryOrderBy, mQueryLimit);
        clearQueryParams();
        return cursorToList(cursor);
    }

    public List<T> queryAll() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClazz), null, null,
                null, null, null, null, null);
        return cursorToList(cursor);
    }

    private void clearQueryParams() {
        // 查询的列
        mQueryColumns = null;
        // 查询的条件
        mQuerySelection = null;
        // 查询的参数
        mQuerySelectionArgs = null;
        // 查询的分组
        mQueryGroupBy = null;
        // 查询对结果集进行过滤
        mQueryHaving = null;
        // 查询排序
        mQueryOrderBy = null;
        // 查询可用于分页
        mQueryLimit = null;
    }


    private List<T> cursorToList(Cursor cursor) {
        List<T> list = null;
        if (cursor != null && cursor.moveToFirst()) {
            try {
                list = new ArrayList<>();
                do {
                    T instance = mClazz.newInstance();
                    Field[] fields = mClazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        //获取角标
                        int index = cursor.getColumnIndex(name);
                        if (index == -1) {
                            continue;
                        }
                        //通过反射获取 游标 的方法
                        Method cursorMethod = cursorMethod(field.getType());
                        if (cursorMethod != null) {
                            Object value = null;
                            value = cursorMethod.invoke(cursor, index);
                            if (value == null) {
                                continue;
                            }

                            //处理一些特殊的部分
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }
                            field.set(instance, value);
                        }
                    }
                    //加入集合
                    list.add(instance);

                } while (cursor.moveToNext());
            } catch (IllegalAccessException e) {
                LogUtils.e(e.toString());
                e.printStackTrace();
            } catch (InstantiationException e) {
                LogUtils.e(e.toString());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                LogUtils.e(e.toString());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                LogUtils.e(e.toString());
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    private Method cursorMethod(Class<?> type) throws NoSuchMethodException {
        String methodName = getColumnMethodName(type);
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        //fieldType.isPrimitive() fieldType 是否是原始类型
        //原始类型：boolean char byte short int long float double
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }

}
