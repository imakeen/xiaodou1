package com.radish.framelibrary.http;

import android.content.Context;
import android.text.TextUtils;

import com.radish.baselibrary.db.DaoSupportFactory;
import com.radish.baselibrary.db.IDaoSupport;
import com.radish.baselibrary.http.EngineCallBack;
import com.radish.baselibrary.http.IHttpEngine;
import com.radish.baselibrary.utils.EncryptUtils;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @作者 radish
 * @创建日期 2018/12/18 10:58 AM
 * @邮箱 15703379121@163.com
 * @描述 OkHttp默认引擎
 */
public class MyOkHttpEngine implements IHttpEngine {
    public static OkHttpClient mOkHttpClient = new OkHttpClient();
    private IDaoSupport<CacheBean> mDaoSupport;
    private final String mCacheDbName = "cache";

    @Override
    public void get(final Context context, final String url, Map<String, Object> params, final boolean isCache, final EngineCallBack callBack) {
        final String joinUrl = jointParams(url, params);
        LogUtils.e("网络请求：MyOkHttpEngine get()---->" + joinUrl);
        // 是否需要缓存
        final String cacheResult = getCache(context, joinUrl, url, isCache, callBack);

        Request request = new Request.Builder()
                .url(joinUrl)
                .tag(context)
                .addHeader("User-Agent", getUserAgent(context))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {
                        if (!isCache || !result.equals(cacheResult)) {
                            // 非缓存或网络数据与缓存数据不一致时
                            if (isCache && !result.equals(cacheResult)) {
                                //保存数据
                                saveCache(context, joinUrl, result);
                            }
                            LogUtils.e(url + " Get返回结果：" + result);
                            callBack.onSuccess(result);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void post(final Context context, final String url, Map<String, Object> params, final boolean isCache, final EngineCallBack callBack) {
        final String joinUrl = jointParams(url, params);
        LogUtils.e("网络请求：MyOkHttpEngine post()---->" + joinUrl);
        // 是否需要缓存
        final String cacheResult = getCache(context, joinUrl, url, isCache, callBack);

        RequestBody requestBody = appendBody(params);
        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .addHeader("User-Agent", getUserAgent(context))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {
                        if (!isCache || !result.equals(cacheResult)) {
                            // 非缓存或网络数据与缓存数据不一致时
                            if (isCache && !result.equals(cacheResult)) {
                                //保存数据
                                saveCache(context, joinUrl, result);
                            }
                            LogUtils.e(url + " Post返回结果：" + result);
                            callBack.onSuccess(result);
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取缓存
     *
     * @param context
     * @param joinUrl
     * @param url
     * @param isCache
     * @param callBack
     * @return
     */
    private String getCache(Context context, String joinUrl, String url, boolean isCache, EngineCallBack callBack) {
        if (isCache) {
            if (mDaoSupport == null)
                mDaoSupport = DaoSupportFactory.getFactory(context.getCacheDir().getPath(), mCacheDbName).getDao(CacheBean.class);

            String joinUrlMd5 = EncryptUtils.encryptMD5ToString(joinUrl);
            LogUtils.e(joinUrlMd5);
            List<CacheBean> cacheList = mDaoSupport.querySupport().selection("urlKey=?").selectionArgs(joinUrlMd5).query();
            if (cacheList != null && cacheList.size() > 0) {
                CacheBean cacheBean = cacheList.get(0);
                String cacheResultJson = cacheBean.getResultJson();
                if (!TextUtils.isEmpty(cacheResultJson)) {
                    LogUtils.e(url + " 缓存：" + cacheResultJson);
                    callBack.onSuccess(cacheResultJson);
                    return cacheResultJson;
                }
            }
        }
        return "";
    }

    /**
     * 保存缓存
     *
     * @param context
     * @param joinUrl
     * @param result
     */
    private void saveCache(Context context, String joinUrl, String result) {
        if (mDaoSupport == null)
            mDaoSupport = DaoSupportFactory.getFactory(context.getCacheDir().getPath(), mCacheDbName).getDao(CacheBean.class);
        String joinUrlMd5 = EncryptUtils.encryptMD5ToString(joinUrl);
        mDaoSupport.delete("urlKey=?", joinUrlMd5);
        mDaoSupport.insert(new CacheBean(joinUrlMd5, result));
        LogUtils.e(joinUrlMd5);
    }

    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    protected String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        } else {
            StringBuffer stringBuffer = new StringBuffer(url);
            if (!url.contains("?")) {
                stringBuffer.append("?");
            } else {
                if (!url.endsWith("?")) {
                    stringBuffer.append("&");
                }
            }
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            return stringBuffer.toString();
        }
    }

    protected void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMineType(file.getAbsolutePath())), file));
                } else if (value instanceof List) {
                    List<File> listFiles = (List<File>) value;
                    for (int i = 0; i < listFiles.size(); i++) {
                        File file = listFiles.get(i);
                        builder.addFormDataPart(key + i, file.getName(), RequestBody.create(MediaType.parse(guessMineType(file.getAbsolutePath())), file));
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    protected String guessMineType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 手机信息
     *
     * @param context
     * @return
     */
    protected static String getUserAgent(Context context) {
        String userAgent = "";
//        APP版本
        String versionName = SystemUtil.getVersionName(context);
//        手机型号
        String systemModel = SystemUtil.getSystemModel();
//        系统版本
        String systemVersion = SystemUtil.getSystemVersion();
        String deviceBrand = SystemUtil.getDeviceBrand();
        userAgent = "Android/" + versionName + "/" + deviceBrand + "" + systemModel + "/" + systemVersion;
        return userAgent;
    }
}
