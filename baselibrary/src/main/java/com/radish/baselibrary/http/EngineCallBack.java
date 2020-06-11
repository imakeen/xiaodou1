package com.radish.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @作者 radish
 * @创建日期 2018/12/18 10:54 AM
 * @邮箱 15703379121@163.com
 * @描述 网络请求回调接口
 */
public interface EngineCallBack {

    //开始执行  重写此方法，实现网络请求之前的信息
    void onPreExecute(Context context, Map<String,Object> params);

    // 错误
    void onError(Exception e);

    // 成功
    void onSuccess(String result);

    // 默认
    EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack(){
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }
    };
}
