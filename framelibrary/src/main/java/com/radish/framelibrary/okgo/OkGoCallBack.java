package com.radish.framelibrary.okgo;

import com.lzy.okgo.model.Response;

/**
 * 普通请求的回调
 */
public abstract class OkGoCallBack implements IOkGoCallBack {


    /**
     * 请求网络成功对数据进行操作，UI线程
     *
     * @param body
     * @param status
     */
    @Override
    public abstract void onSuccess(String body, int status);

    /**
     * 请求网络失败，UI线程
     *
     * @param response
     */
    @Override
    public void onError(Response<String> response) {
    }
}
