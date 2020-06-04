package com.radish.baselibrary.utils;

import java.lang.reflect.Field;

/**
 * @作者 radish
 * @创建日期 2018/12/26 10:28 AM
 * @邮箱 15703379121@163.com
 * @描述 字符串转换工具类
 */
public class StringUtils {
    public static String obj2Json(Object obj) {
        StringBuffer buffer = new StringBuffer("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(obj);
                buffer.append("\n\t\"" + name + "\":\"" + value + "\",");
            }
            buffer.replace(buffer.length() - 1, buffer.length(), "\n}");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
