package com.radish.baselibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.radish.baselibrary.base.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ 创建时间: 2017/5/3 .
 * @ 描述：联网工具类
 */

public class MyHttpUtils {

    /**
     * post 传输
      * @param context
     * @param map
     * @param handler
     * @param rightCode
     * @param errorCode
     * @param url
     */
    public static void okHttpUtilsHead(Context context, HashMap map, final Handler handler, final int rightCode, final int errorCode, final String url) {

        if (!NetUtils.isNetAvailable(context)) {
            try {
                ToastUtil.showLong(BaseApplication.getInstance(), "网络不可用，请检查网络");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter1 = map.entrySet().iterator();
        while (iter1.hasNext()) {
            Map.Entry entry = (Map.Entry) iter1.next();
            builder.add(entry.getKey() + "", entry.getValue() + "");
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", getUserAgent(context))
//                .addHeader("token", "token")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(url + " MyHttpUtil:call--error");
                Message message = handler.obtainMessage();
                message.what = errorCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                LogUtils.e(url + " MyHttpUtil:call--" + json);
                try {
                    JSONObject object = new JSONObject(json);
//                    Looper.prepare();
////                        主线程操作
//                    Looper.loop();
                    Message message = handler.obtainMessage();
                    message.obj = json;
                    message.what = rightCode;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    try {
                        LogUtils.e("MyHttpUtil:call----" + url);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * get 传输
     * @param handler
     * @param rightCode
     * @param erroCode
     * @param url
     */
    public static void okHttpGet(final Handler handler, final int rightCode, final int erroCode, final String url) {
        if (!NetUtils.isNetAvailable(BaseApplication.getInstance())) {
            try {
                ToastUtil.showLong(BaseApplication.getInstance(), "网络不可用，请检查网络");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        OkHttpClient mHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
//                .addHeader("User-Agent", getUserAgent(context))
//                .addHeader("token",  "token")
                .build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(url + " MyHttpUtil:call--error");
                Message message = handler.obtainMessage();
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                String json = response.body().string();
                message.obj = json;
                LogUtils.e(url + " MyHttpUtil:call--" + json);
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });
    }

    public static String getUserAgent(Context context) {
        String userAgent = "";
//        APP版本
        String versionName = SystemUtil.getVersionName(context);
//        手机型号
        String systemModel = SystemUtil.getSystemModel();
//        系统版本
        String systemVersion = SystemUtil.getSystemVersion();
        String deviceBrand = SystemUtil.getDeviceBrand();
        userAgent = "Android/" + versionName + "/" + deviceBrand + "" + systemModel + "/" + systemVersion;
        LogUtils.e("UserAgent  " + userAgent);
        return userAgent;
    }
}
