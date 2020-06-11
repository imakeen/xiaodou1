package com.xinzu.xiaodou.pro.activity.registerlogin;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestCallBack;

import java.util.HashMap;

import javax.inject.Inject;


public class RegisterPresenter extends BasePAV<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    RegisterPresenter() {

    }


    @Override
    public void getsendRegistSms(String mobile, Context context) {
        new Thread(() -> {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("phone", mobile);
            OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
            okHttpRequestUtils.requestAsynjson(ApiService.getMsgCode, new Gson().toJson(hashMap), new RequestCallBack() {
                @Override
                public void onRequestSuccess(Object result) {
                    mView.getmsgcode(result.toString());
                }

                @Override
                public void onRequestFailed(String errorMsg) {

                }
            });
        }).start();
    }

    @Override
    public void login(String phone, String msgcode, Context context) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("phone", phone);
        hashMap.put("msgCode", msgcode);
        mView.showLoading();
        new Thread(() -> {
            OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
            okHttpRequestUtils.requestAsynjson(ApiService.loging, new Gson().toJson(hashMap), new RequestCallBack() {
                @Override
                public void onRequestSuccess(Object result) {
                    mView.closeLoading();
                    mView.getloginresult(result.toString());
                }

                @Override
                public void onRequestFailed(String errorMsg) {

                }
            });
        }).start();

    }
}

