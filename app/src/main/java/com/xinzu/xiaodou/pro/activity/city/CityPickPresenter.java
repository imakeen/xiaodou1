package com.xinzu.xiaodou.pro.activity.city;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.activity.login.LoginContract;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import javax.inject.Inject;


public class CityPickPresenter extends BasePAV<CityPickContract.View> implements CityPickContract.Presenter {

    @Inject
    CityPickPresenter() {

    }

    @Override
    public void getCity(String appKey, String timeStamp, String sign, Context context) {
        mView.showLoading();
        Hashtable<String, String> hashMap = new Hashtable<>();
        hashMap.put("appKey", appKey);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("sign", sign);
        MyApp.apiService(ApiService.class)
                .collectCityInfo(RequestBodyUtil.hashtableRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {
                    mView.showLoading();
                })
                .doFinally(() -> {
                    mView.closeLoading();
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(bean -> {
                    mView.getCity(bean.string());
                }, throwable -> {
                    com.radish.baselibrary.utils.LogUtils.e("联网失败：" + throwable.toString());
                });


//        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
//        okHttpRequestUtils.requestAsyn(ApiService.collectCityInfo, 1, hashMap, new RequestCallBack() {
//            @Override
//            public void onRequestSuccess(Object result) {
//                mView.closeLoading();
//                LogUtils.e(result.toString());
//                mView.getCity(result.toString());
//            }
//
//            @Override
//            public void onRequestFailed(String errorMsg) {
//                mView.closeLoading();
//            }
//        });
    }
}
