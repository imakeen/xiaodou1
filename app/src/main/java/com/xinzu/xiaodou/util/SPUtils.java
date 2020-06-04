package com.xinzu.xiaodou.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.SharedPreferencesHelper;

import java.util.Locale;

public class SPUtils {


    public static String getCurrentLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("languageInfos", Context.MODE_PRIVATE);
        String lastLanguage = sharedPreferences.getString("language", Locale.getDefault().getLanguage()/* Locale.ENGLISH.getLanguage()*/);
        LogUtils.e("lastLanguage:" + lastLanguage);
        return lastLanguage;
    }

    public static void saveCurrentLanguage(Context context, String language) {
        LogUtils.e("saveCurrentLanguage:" + language);
        SharedPreferences sharedPreferences = context.getSharedPreferences("languageInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.commit();
    }


    public static void saveLoginInfo(Context context, String logininfo, String token, String uid) {
        SharedPreferencesHelper.saveToken(token);
        SharedPreferencesHelper.saveUserId(uid);
        SharedPreferences sharedPreferences = context.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("logininfo", logininfo);
        editor.commit();
    }


    public static void saveLoginInfo(Context context, String logininfo) {
        saveLoginInfo(context, logininfo, "", "");
    }

    public static String getLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String lastLanguage = sharedPreferences.getString("logininfo", "");
        return lastLanguage;
    }
}
