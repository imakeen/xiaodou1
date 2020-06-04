package com.radish.baselibrary.db;

import android.text.TextUtils;

/**
 * @作者 radish
 * @创建日期 2018/12/19 2:41 PM
 * @邮箱 15703379121@163.com
 * @描述 数据库工具包
 */
public class DaoUtil {
    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text";
        } else if (type.contains("int")) {
            value = " integer";
        } else if (type.contains("float")) {
            value = " float";
        } else if (type.contains("double")) {
            value = " double";
        } else if (type.contains("char")) {
            value = " varchar";
        } else if (type.contains("long")) {
            value = " long";
        }
        return value;
    }

    public static <T> String getTableName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    public static String capitalize(String name) {
        if (!TextUtils.isEmpty(name) && name.length() > 1)
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
