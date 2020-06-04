package com.radish.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.radish.baselibrary.base.BaseApplication;

public class SharedPreferencesHelper {

    private static final String KEY_SPS_NAME = "SharedPreferences_name";
    private static final String KEY_UserId = "key_userId";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_NAME = "key_userName";
    private static final String KEY_USER_IMG = "key_userImg";
    private static final String KEY_USER_PHONE = "key_userPhone";


    private static SharedPreferences sp;

    static {
        sp = BaseApplication.getInstance().getSharedPreferences(KEY_SPS_NAME, Context.MODE_PRIVATE);
    }

    //--------------------UserID----------------------------------------
    public static void saveUserId(String userInfo) {
        sp.edit().putString(KEY_UserId, userInfo).apply();
    }


    public static String getUserId() {
        return sp.getString(KEY_UserId, null);
    }

    //--------------------UserName----------------------------------------

    public static void saveUserName(String userName) {
        sp.edit().putString(KEY_USER_NAME, userName).apply();
    }


    public static String getUserName() {
        return sp.getString(KEY_USER_NAME, "");
    }

    //--------------------UserImg----------------------------------------
    public static void saveUserImg(String saveUserImg) {
        sp.edit().putString(KEY_USER_IMG, saveUserImg).apply();
    }

    public static String getUserImg() {
        return sp.getString(KEY_USER_IMG, "");
    }

    //--------------------UserPone----------------------------------------

    public static void saveUserPhone(String phone) {
        sp.edit().putString(KEY_USER_PHONE, phone).apply();
    }

    public static String getUserPhone() {
        return sp.getString(KEY_USER_PHONE, "");
    }

    //--------------------Token----------------------------------------

    public static void saveToken(String carnum) {
        sp.edit().putString(KEY_TOKEN, carnum).apply();
    }

    public static String getToken() {
        return sp.getString(KEY_TOKEN, "");
    }

    public static void clearTokenAndUserId() {
        sp.edit().clear().apply();
    }
}
