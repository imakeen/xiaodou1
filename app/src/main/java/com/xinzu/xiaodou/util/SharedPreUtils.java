package com.xinzu.xiaodou.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.xinzu.xiaodou.MyApp;


/**
 * Created by yangxiaoling on 2019/12/9 0009.
 * desc : sp工具类
 * version: 1.0
 
 
 
 */
public class SharedPreUtils {
    private static final String SHARED_NAME = "WeYue_SP";
    private static SharedPreUtils sInstance;
    private static SharedPreferences sharedReadable;
    private static SharedPreferences.Editor sharedWritable;

    private SharedPreUtils() {
        sharedReadable = MyApp.getInstance()
                .getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS);
        sharedWritable = sharedReadable.edit();
    }

    public static SharedPreUtils getInstance() {
        if (sInstance == null) {
            synchronized (SharedPreUtils.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * 清除本地数据
     */
    public void sharedPreClear() {
        sharedWritable.clear().apply();
    }

    /**
     * 清除本地数据指定key
     */
    public void sharedPreRemove(String key) {
        sharedWritable.remove(key).apply();
    }

    public String getString(String key, String defValue) {
        return sharedReadable.getString(key, defValue);
    }

    public void putString(String key, String value) {
        sharedWritable.putString(key, value);
        sharedWritable.apply();
    }

    public void putInt(String key, int value) {
        sharedWritable.putInt(key, value);
        sharedWritable.apply();
    }

    public static void putBoolean(String key, boolean value) {
        sharedWritable.putBoolean(key, value);
        sharedWritable.apply();
    }

    public int getInt(String key, int def) {
        return sharedReadable.getInt(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return sharedReadable.getBoolean(key, def);
    }
}