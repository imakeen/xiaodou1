package com.xinzu.xiaodou.http;

public interface RequestProgressCallBack<T> extends RequestCallBack<T> {
	
	/**
	 * 响应进度更新
	 */
	void onProgress(long total, long current);
}
