package com.xinzu.xiaodou.util;

/**
 * Description: 下载进度回调
 * Created by jia on 2017/11/30.
 * 人之所以能，是相信能
 */
 
 
public interface DownloadListener {

    void downLoadSucces();
    void downLoadprogress(int progress);
    void downLoadError(String e);

}
