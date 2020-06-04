package com.radish.baselibrary.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @作者 radish
 * @创建日期 2018/12/18 10:52 AM
 * @邮箱 15703379121@163.com
 * @描述 网络请求工具类
 */
public class HttpUtils {

    private String mUrl;
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0X0022;

    private Context mContext;
    private Map<String, Object> mParams;
    //是否读取缓存
    private boolean mCarChe = false;

    private HttpUtils(Context context) {
        this.mContext = context;
        this.mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    // 默认的引擎
    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    // 在Application里初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    // 自己带引擎
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils cache(boolean isCache) {
        this.mCarChe = isCache;
        return this;
    }

    public HttpUtils post() {
        this.mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        this.mType = GET_TYPE;
        return this;
    }

    //添加参数
    public HttpUtils addParam(String key, Object value) {
        this.mParams.put(key, value);
        return this;
    }

    //添加参数
    public HttpUtils addParams(Map<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }


    //添加回调
    public void execute() {
        execute(null);
    }

    public void execute(EngineCallBack callBack) {
        if (callBack == null) {
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }
        callBack.onPreExecute(this.mContext, this.mParams);
        if (this.mType == POST_TYPE) {
            post(this.mUrl, this.mParams, callBack);
        }
        if (this.mType == GET_TYPE) {
            get(this.mUrl, this.mParams, callBack);
        }
    }

    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext, url, params, mCarChe, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext, url, params, mCarChe, callBack);
    }

    public static Class<?> analysisClazzInfo(Object object) {
        Type rootType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) rootType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
    //    get请求

    //    post请求

    //    下载文件

    //    上传文件

    //    https 添加证书
}
