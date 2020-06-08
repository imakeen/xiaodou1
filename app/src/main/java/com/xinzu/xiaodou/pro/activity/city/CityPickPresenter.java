package com.xinzu.xiaodou.pro.activity.city;

import android.arch.lifecycle.LifecycleOwner;

import com.radish.baselibrary.utils.LogUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.activity.login.LoginContract;

import javax.inject.Inject;


public class CityPickPresenter extends BasePAV<CityPickContract.View> implements CityPickContract.Presenter {

    @Inject
    CityPickPresenter() {

    }

    @Override
    public void getCity(String appKey, String timeStamp, String sign) {
        MyApp.apiService(ApiService.class)
                .collectCityInfo(appKey, timeStamp, sign)
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {
                    mView.showLoading();
                })
                .doFinally(() -> {
                    mView.closeLoading();
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String jsonObject) {
                        LogUtils.e(jsonObject);
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                    mView.onFail();
                });
    }
}
