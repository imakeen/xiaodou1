package com.radish.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @作者 radish
 * @创建日期 2018/12/18 10:52 AM
 * @邮箱 15703379121@163.com
 * @描述 网络请求工具类
 */
public interface IHttpEngine {
    //    get请求
    void get(Context context,String url, Map<String, Object> params,boolean isCache,EngineCallBack callBack);

    //    post请求
    void post(Context context,String url, Map<String, Object> params,boolean isCache, EngineCallBack callBack);

    //    下载文件

    //    上传文件

    //    https 添加证书
}
