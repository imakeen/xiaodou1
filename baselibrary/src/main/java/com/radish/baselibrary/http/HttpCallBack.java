package com.radish.baselibrary.http;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @作者 radish
 * @创建日期 2018/12/18 5:05 PM
 * @邮箱 15703379121@163.com
 * @描述 根据不同的APP，有不同的处理
 */
public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        // 网络访问前的处理

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T data = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(data);
    }

    //返回直接可以操作的对象
    public abstract void onSuccess(T result);
}
