package com.radish.framelibrary.okgo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.radish.baselibrary.utils.DensityUtil;
import com.radish.baselibrary.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class OkGoUtils {

    private static LoadingDialog loadingDialog;

    /**
     * 不带加载动画的普通的post请求
     *
     * @param url      请求的网络地址
     * @param params   请求的参数
     * @param callBack 请求的回调
     */
    public static void normalRequest(final String url, HttpParams params, final OkGoCallBack callBack) {
        params = initParams(params);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                params(params).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        loadSuccess(url, response, callBack);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        LogUtils.e("onCacheSuccess:" + response.toString());
                        loadSuccess(url, response, callBack);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.e(url + "  response失败：" + response.body() + "=============" + response.message());
                        if (callBack != null) {
                            callBack.onError(response);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    public static void normalRequestGet(final String url, final OkGoCallBack callBack) {
        OkGo.<String>get(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                execute(new Callback<String>() {


            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {

            }

            @Override
            public void onSuccess(Response<String> response) {
                callBack.onSuccess(response.body(), 0);
            }

            @Override
            public void onCacheSuccess(Response<String> response) {
                callBack.onSuccess(response.body(), 0);
            }

            @Override
            public void onError(Response<String> response) {
                if (callBack != null) {
                    LogUtils.e(url + " response失败：" + response.body() + "=============" + response.message());
                    callBack.onError(response);
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void uploadProgress(Progress progress) {

            }

            @Override
            public void downloadProgress(Progress progress) {

            }

            @Override
            public String convertResponse(okhttp3.Response response) throws Throwable {
                String result = response.body().string();
                return result;
            }
        });

    }

    /**
     * 处理参数
     * @param params
     * @return
     */
    private static HttpParams initParams(HttpParams params) {
        if (params == null) {
            params = new HttpParams();
        };
        LinkedHashMap<String, List<String>> map = params.urlParamsMap;
        StringBuilder mapStr = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            mapStr.append("Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n");
        }
        LogUtils.e(mapStr.toString());
        return params;
    }

//    private static String getUrl(String url) {
//        return MyUrl.ServiceMidUrl + url;
//    }

    /**
     * 带加载动画的普通的post请求
     *
     * @param url      请求的网络地址
     * @param context  当前Avtivity
     * @param params   请求的参数
     * @param callBack 请求的回调
     */
    public static void progressRequest(final String url, final Context context, HttpParams params, final OkGoCallBack callBack) {
        params = initParams(params);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(50000).//缓存时间
                params(params).
                execute(new Callback<String>() {
                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        loadSuccess(url, response, callBack);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        loadSuccess(url, response, callBack);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.e(url + " response失败：" + response.body() + "=============" + response.message());
                        callBack.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    private static void loadSuccess(String url, Response<String> response, final OkGoCallBack callBack) {
        String body = response.body();
        LogUtils.e(url + "获取网络数据:" + body);
        if (callBack != null) {
            try {
                JSONObject jsonObject = new JSONObject(body);

                callBack.onSuccess(body, 0);
            } catch (JSONException e) {
                LogUtils.e("异常：" + e.toString());
                e.printStackTrace();
            }
        }

    }

    private static void loadSuccess(String url, Response<String> response, OnUploadFileListener onUploadFileListener) {
        if (onUploadFileListener != null) {
            String body = response.body();
            LogUtils.e(url + "获取网络数据:" + body);
            try {
                JSONObject jsonObject = new JSONObject(body);
                onUploadFileListener.onSuccess(body, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 下载文件请求
     *
     * @param url                    链接地址
     * @param tag                    tag，当前Activity
     * @param destFileDir            下载文件的存储路径
     * @param destFileName           下载文件的存储名称
     * @param onDownloadFileListener 下载任务的回调
     */
    public static void downloadFile(final String url, Activity tag, String destFileDir, String destFileName, final OnDownloadFileListener onDownloadFileListener) {
        OkGo.<File>get(url).tag(tag).
                execute(new FileCallback(destFileDir, destFileName) {

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                        onDownloadFileListener.onStart();
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        onDownloadFileListener.onSuccess(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        onDownloadFileListener.onDownloadProgress(progress);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        LogUtils.e(url + " response失败：" + response.body() + "=============" + response.message());
                        onDownloadFileListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        onDownloadFileListener.onFinish();
                    }
                });
    }

    /**
     * 上传单个文件的请求
     *
     * @param url                  链接地址
     * @param tag                  tag，当前Activity
     * @param params               参数，可以为null
     * @param fileKey              文件对应的key
     * @param file                 要上传的文件
     * @param onUploadFileListener 上传文件的回调
     */
    public static void upLoadFile(final String url, final Activity tag, HttpParams params, String fileKey, File file, final OnUploadFileListener onUploadFileListener) {
        initParams(params);
        OkGo.<String>post(url).tag(tag)
                .params(params)
                .params(fileKey, file, file.getName())
                .execute(new StringCallback() {

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                        onUploadFileListener.onStart();
                        initDialog(tag, url);
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        onUploadFileListener.onUploadProgress(progress);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        loadSuccess(url, response, onUploadFileListener);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.e(url + "  response失败：" + response.body() + "=============" + response.message());
                        onUploadFileListener.onError(response);
                        dismisDialog();
                    }

                    @Override
                    public void onFinish() {
                        onUploadFileListener.onFinish();
                        dismisDialog();
                    }
                });
    }

    /**
     * 上传多个文件的请求
     *
     * @param url                  链接地址
     * @param tag                  tag，当前Activity
     * @param params               参数，可以为null
     * @param filesKey             文件对应的key
     * @param fileList             要上传的文件集合
     * @param onUploadFileListener 上传文件的回调
     */
    public static void upLoadFiles(final String url, Activity tag, HttpParams params, String filesKey, List<File> fileList, final OnUploadFileListener onUploadFileListener) {
        params = initParams(params);
        PostRequest<String> postRequest = OkGo.<String>post(url).tag(tag);
        //判断如果params不为空，因为上传文件时可能不会有params
        if (params != null) {
            postRequest.params(params);
        }
        postRequest.addFileParams(filesKey, fileList).
                execute(new StringCallback() {

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                        onUploadFileListener.onStart();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        onUploadFileListener.onUploadProgress(progress);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        loadSuccess(url, response, onUploadFileListener);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.e(url + " response失败：" + response.body() + "=============" + response.message());
                        onUploadFileListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        onUploadFileListener.onFinish();
                    }
                });
    }

    /**
     * 初始化加载过程dialog
     */
    private static void initDialog(final Context context, final String url) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        }
        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();

        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = DensityUtil.dp2px(context,100);
        attributes.height = DensityUtil.dp2px(context,100);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(attributes);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //去掉背景色（一些设备上由于系统主题原因会有背景边框）

        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //取消全局默认的OkHttpClient中标识为tag的请求
                OkGo.getInstance().cancelTag(url);
            }
        });
    }

    /**
     * 取消dialog
     */
    private static void dismisDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }
}
