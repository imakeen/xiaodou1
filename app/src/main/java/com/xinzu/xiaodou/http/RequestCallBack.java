package com.xinzu.xiaodou.http;

public interface RequestCallBack<T> {

	/**
     * 响应成功
     */
     void onRequestSuccess(T result);

    /**
     * 响应失败
     */
     void onRequestFailed(String errorMsg);
     
}
