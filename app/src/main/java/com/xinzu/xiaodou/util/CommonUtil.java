package com.xinzu.xiaodou.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.radish.baselibrary.Intent.IntentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.util.refresh.MyHouseListFooter;
import com.xinzu.xiaodou.util.refresh.MyHouseListHeader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CommonUtil {

    public static final String WECHAT_APPID = "";
    public static final String WECHAT_APPSECRET = "";


    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String TITLE = "title";

    public static void quitActivity(Activity activity, String toast) {
        if (!TextUtils.isEmpty(toast)) {
            ToastUtils.showShort(toast);
        }
        new Handler().postDelayed(() -> activity.finish(), 500);
    }

//    public static String getImageUrl(String url) {
////        url = getStr(url);
////        if (url.startsWith("http://") || url.startsWith("https://")) {
////            return url;
////        }
////        return ApiService.imageurl + url;
//    }

    public static void startIntent(Context context, Class clazz) {
        IntentUtils.getInstance().with(context, clazz).start();
    }

    public static void startIntent(Context context, Class clazz, int id) {
        IntentUtils.getInstance().with(context, clazz).putInt(ID, id).start();
    }

    public static void startIntent(Context context, Class clazz, int id, int type) {
        IntentUtils.getInstance().with(context, clazz).putInt(TYPE, type).putInt(ID, id).start();
    }

    public static void startIntentType(Context context, Class clazz, int type) {
        IntentUtils.getInstance().with(context, clazz).putInt(TYPE, type).start();
    }


    public static void startIntentTitle(Context context, Class clazz, String title) {
        IntentUtils.getInstance().with(context, clazz).putString(TITLE, title).start();
    }

    public static boolean checkViewEmpty(TextView tv) {
        return checkViewEmpty(null, tv);
    }

    public static boolean checkViewEmpty(String[] msgs, TextView... tvs) {
        for (int i = 0; i < tvs.length; i++) {
            String msg = null;
            if (msgs != null && msgs.length > i) {
                msg = msgs[i];
            }
            if (checkViewEmpty(msg, tvs[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkViewEmpty(TextView... tvs) {
        return checkViewEmpty(null, tvs);
    }

    public static boolean checkViewEmpty(String msg, TextView tv) {
        if (tv != null) {
            if (!TextUtils.isEmpty(tv.getText().toString().trim())) {
                return false;
            }
            if (!TextUtils.isEmpty(msg)) {
                ToastUtils.showShort(msg);
            }
            return true;
        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
        }
        return true;
    }

    public static String getStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    public static String getStr(String str, String defaultStr) {
        if (TextUtils.isEmpty(str)) {
            return getStr(defaultStr);
        }
        return str;
    }

    public static List getList(int size) {
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            list.add(new Object());
        }
        return list;
    }

    public static <T> List<T> getList(int size, T t) {
        List<T> list = new ArrayList();
        for (int i = 0; i < size; i++) {
            list.add(t);
        }
        return list;
    }

    public static Class<?> analysisClazzInfo(Object object) {
        Type rootType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) rootType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    public static <T> List<T> getList(List<T> list, int count) {
        List<T> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i >= count) {
                break;
            }
            tempList.add(list.get(i));
        }
        return tempList;
    }

    public static int getRandom(int n) {
        return new Random().nextInt(n);
    }

    public static void initRefresh(Activity context, SmartRefreshLayout refreshLayout) {
        refreshLayout.setRefreshHeader(new MyHouseListHeader(context, (ViewGroup) context.getWindow().getDecorView()));
        refreshLayout.setRefreshFooter(new MyHouseListFooter(context, (ViewGroup) context.getWindow().getDecorView()));
    }


    public static void setText(TextView tv, String text) {
        if (tv != null)
            tv.setText(CommonUtil.getStr(text));
    }

    public static void setEditDisable(TextView tv, boolean editable) {
        tv.setEnabled(editable);
        tv.setFocusable(editable);
        if (!editable)
            tv.setKeyListener(null);
    }

    public static String getFileName(String url) {
        String fileName = CommonUtil.getStr(url);
        if (!TextUtils.isEmpty(url)) {
            String[] split = url.split("/");
            if (split != null && split.length > 0) {
                fileName = split[split.length - 1];
            }
        }
        return fileName;
    }

}
