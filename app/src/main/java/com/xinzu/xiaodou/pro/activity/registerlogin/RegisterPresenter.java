package com.xinzu.xiaodou.pro.activity.registerlogin;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.google.gson.Gson;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;

import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;

import java.util.HashMap;
import java.util.Map;

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
//        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("phone", mobile);
//        MyApp.apiService(ApiService.class)
//                .getMsgCode(hashMap)
//                .compose(RxSchedulers.io_main())
//                .doOnSubscribe(d -> {
//                    mView.showLoading();
//                })
//                .doFinally(() -> {
//                    mView.closeLoading();
//                })
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
//                .subscribe(new SuccessfulConsumer() {
//                    @Override
//                    public void success(String jsonObject) {
//                        ToastUtil.showShort(jsonObject);
//                    }
//                }, throwable -> {
//                    LogUtils.e("联网失败：" + throwable.toString());
//                    mView.onFail();
//                });

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

