package com.xinzu.xiaodou.http;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;


import com.google.gson.Gson;
import com.xinzu.xiaodou.util.GsonUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequestUtils {

    // mdiatype // 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    // mdiatype // 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    private static final String TAG = OkHttpRequestUtils.class.getSimpleName();
    private static String BASE_URL = null; // 请求接口根地址
    private static volatile OkHttpRequestUtils mInstance;// 单例引用
    private OkHttpClient mOkHttpClient;// okHttpClient 实例
    private Handler okHttpHandler;// 全局处理子线程和M主线程通信

    /**
     * 初始化RequestManager
     */
    private OkHttpRequestUtils(Context context) {

        // 请求接口根地址
        BASE_URL = "https://a.xinzuchuxing.com/AdminConsult/insert";

        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(100, TimeUnit.SECONDS)// 设置超时时间
                .readTimeout(100, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(100, TimeUnit.SECONDS)// 设置写入超时时间
                .build();
        // 初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static OkHttpRequestUtils getInstance(Context context) {
        OkHttpRequestUtils inst = mInstance;
        if (inst == null) {
            synchronized (OkHttpRequestUtils.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new OkHttpRequestUtils(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public <T> Call requestAsynjson(String actionUrl, String json
                                    ,
                                    RequestCallBack<T> callBack) {

        Call call = null;
        if (actionUrl!=null&&!actionUrl.isEmpty()) {
            call = requestPostByAsynjson(actionUrl, json, callBack);
        } else {
            call = noUrlByAsynjson(json, callBack);
        }
        return call;
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByAsynjson(String actionUrl, String json,
                                          final RequestCallBack<T> callBack) {
        try {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            String requestUrl = String.format("%s%s", ApiService.ServiceUrl, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.i(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public <T> Call noUrlByAsynjson(String json,
                                    final RequestCallBack<T> callBack) {
        try {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            final Request request = addHeaders().url(BASE_URL).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.i(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder().addHeader("Connection", "keep-alive")
                // .addHeader("Connection", "close")
                .addHeader("platform", "2").addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE).addHeader("appVersion", "3.9.0");
        return builder;
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final RequestCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onRequestSuccess(result);
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final RequestCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onRequestFailed(errorMsg);
                }
            }
        });
    }

    /**
     * 统一处理进度信息
     *
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final RequestProgressCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
    }
}