package com.xinzu.xiaodou.util;

import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.ActivityCollector;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.pro.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class CommentUtil {
    public static <T> List<T> getList(T bean, int size) {
        List<T> list = new ArrayList<T>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                list.add(bean);
            }
        }
        return list;
    }

    public static List getList(int size) {
        List list = new ArrayList();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                list.add(new Object());
            }
        }
        return list;
    }

    public static void openWebActivity(String title, String url) {
        if (TextUtils.isEmpty(url)) {
            LogUtils.e("网页错误！");
            return;
        }
//        IntentUtils.getInstance().with(MyApp.getInstance(), MyWebActivity.class)
//                .putString("title", getStr(title))
//                .putString("url", url)
//                .start();
    }

    public static void openWebActivity(String url) {
        LogUtils.e("url:" + url);
        if (TextUtils.isEmpty(url)) {
            LogUtils.e("网页错误！");
            return;
        }
//        IntentUtils.getInstance().with(MyApp.getInstance(), MyWebActivity.class)
//                .putString("url", url)
//                .start();
    }

    public static String checkJsonToString(JsonObject jsonObject, String key) {
        if (jsonObject != null && !TextUtils.isEmpty(key) && jsonObject.has(getStr(key)) && jsonObject.get(getStr(key)) != null && !TextUtils.equals("null", jsonObject.get(key).toString())) {
            LogUtils.e("???");
            return getStr(jsonObject.get(key).getAsString());
        }
        return "";
    }

    public static String getStr(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        return str;
    }

    public static String getStr(String str, String defaultStr) {
        if (TextUtils.isEmpty(str))
            return getStr(defaultStr);
        return str;
    }


    public static boolean checkEmpty(String msg, TextView textView) {
        if (textView == null || TextUtils.isEmpty(textView.getText().toString().trim())) {
            ToastUtil.showShort(getStr(msg));
            return true;
        }
        return false;
    }

    public static boolean checkEmpty(String[] msgs, TextView... textViews) {
        if (textViews == null || textViews.length == 0)
            return false;
        for (int i = 0; i < textViews.length; i++) {
            if (checkEmpty(msgs != null && msgs.length > i ? msgs[i] : "", textViews[i]))
                return true;
        }
        return false;
    }

    public static void startLogin() {
        startActivity(LoginActivity.class);
        ActivityCollector.removeAll(LoginActivity.class, MainActivity.class);
    }

    public static void startMain() {
        startActivity(MainActivity.class);
        ActivityCollector.removeAll(MainActivity.class);
    }

    public static void startMainKeep() {
        if (ActivityCollector.getActivity(MainActivity.class) == null)
            startActivity(MainActivity.class);
        ActivityCollector.removeAll(MainActivity.class);
    }

    public static void startActivity(Class clazz) {
        IntentUtils.getInstance().with(MyApp.getInstance(), clazz).start();
    }
}
