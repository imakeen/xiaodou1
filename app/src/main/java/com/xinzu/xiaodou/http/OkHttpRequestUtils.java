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
    private static final int TYPE_GET = 0;// get请求
    private static final int TYPE_POST_JSON = 1;// post请求参数为json
    private static final int TYPE_POST_FORM = 2;// post请求参数为表单

    private static String BASE_URL = null; // 请求接口根地址
    private static volatile OkHttpRequestUtils mInstance;// 单例引用
    private OkHttpClient mOkHttpClient;// okHttpClient 实例
    private Handler okHttpHandler;// 全局处理子线程和M主线程通信

    /**
     * 初始化RequestManager
     */
    private OkHttpRequestUtils(Context context) {

        // 请求接口根地址
        BASE_URL = ApiService.ServiceUrl;

        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)// 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)// 设置写入超时时间
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

    /**
     * okHttp同步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型 0:GET 1:POST 2:POST From
     * @param paramsMap   请求参数
     */
    public String requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
        String result = null;
        switch (requestType) {
            case TYPE_GET:
                result = requestGetBySyn(actionUrl, paramsMap);
                break;
            case TYPE_POST_JSON:
                result = requestPostBySyn(actionUrl, paramsMap);
                break;
            case TYPE_POST_FORM:
                result = requestPostBySynWithForm(actionUrl, paramsMap);
                break;
        }
        return result;
    }

    public String insertsign(String actionUrl, int requestType, String params) {

        try {
            // 补全请求地址
            String requestUrl = String.format("%s%s", BASE_URL, actionUrl);
            // 创建一个请求实体对象 RequestBody
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            // 创建一个请求
            final Request request = addHeaders().url(requestUrl).post(body).build();
            // 创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            // 执行请求
            Response response = call.execute();
            String a = response.body().string();
            // 请求执行成功
            if (response.isSuccessful()) {
                String result = a;
                Log.i(TAG, "response ----->" + result);
                return result;
            } else {
                throw new IOException("Unexpected code" + response);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private String requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        String result = "";
        try {
            StringBuilder tempParams = new StringBuilder();
            // 处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                // 对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            // 补全请求地址
            String requestUrl = String.format("%s%s?%s", BASE_URL, actionUrl, tempParams.toString());
            // 创建一个请求
            Request request = addHeaders().url(requestUrl).build();
            // 创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            // 执行请求
            final Response response = call.execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                Log.i(TAG, "response ----->" + result);

                //System.out.println("dfsdf");
                return result;
            } else {
                throw new IOException("Unexpected code" + response);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return result;
        }
    }

    /**
     * okHttp post同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private String requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        String result = "";
        try {
            // 处理参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            int ii = paramsMap.size();
            for (String key : paramsMap.keySet()) {
                if (pos > 0 && pos < ii) {
                    tempParams.append(",");
                }
                tempParams.append(String.format("%s:%s", ("\"" + key + "\""), ("\"" + paramsMap.get(key)/*URLEncoder.encode(paramsMap.get(key), "utf-8")*/) + "\""));
                pos++;
            }

            // 补全请求地址
            String requestUrl = String.format("%s%s", BASE_URL, actionUrl);
            // 生成参数
            String json = "{" + tempParams + "}";
            String params = json;
            // 创建一个请求实体对象 RequestBody
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            // 创建一个请求
            final Request request = addHeaders().url(requestUrl).post(body).build();
            // 创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            // 执行请求
            Response response = call.execute();
            // 请求执行成功
            if (response.isSuccessful()) {
                result = response.body().string();
                //Log.i(TAG, "response ----->" + result);
                return result;
            } else {
                throw new IOException("Unexpected code" + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private String requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            // 创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                // 追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            // 生成表单实体对象
            RequestBody formBody = builder.build();
            // 补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            // 创建一个请求
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            // 创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            // 执行请求
            Response response = call.execute();

            if (response.isSuccessful()) {
                String result = response.body().string();
                Log.i(TAG, "response ----->" + result);
                return result;
            } else {
                throw new IOException("Unexpected code" + response);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * okHttp异步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     * @param <T>         数据泛型
     **/
    public <T> Call requestAsyn(String actionUrl, int requestType, HashMap<String, String> paramsMap,
                                RequestCallBack<T> callBack) {
        Call call = null;
        switch (requestType) {
            case TYPE_GET:
                call = requestGetByAsyn(actionUrl, paramsMap, callBack);
                break;
            case TYPE_POST_JSON:
                call = requestPostByAsyn(actionUrl, paramsMap, callBack);
                break;
            case TYPE_POST_FORM:
                call = requestPostByAsynWithForm(actionUrl, paramsMap, callBack);
                break;
        }
        return call;
    }

    public <T> Call requestAsynjson(String actionUrl, String json,
                                    RequestCallBack<T> callBack) {
        Call call = null;
        call = requestPostByAsynjson(actionUrl, json, callBack);
        return call;
    }

    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap,
                                      final RequestCallBack<T> callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
            final Request request = addHeaders().url(requestUrl).build();
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
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap,
                                      final RequestCallBack<T> callBack) {
        try {
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(paramsMap));
            String requestUrl = String.format("%s%s", BASE_URL, actionUrl);
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

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByAsynjson(String actionUrl, String json,
                                          final RequestCallBack<T> callBack) {
        try {

            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            String requestUrl = String.format("%s%s", BASE_URL, actionUrl);
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

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap,
                                               final RequestCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
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