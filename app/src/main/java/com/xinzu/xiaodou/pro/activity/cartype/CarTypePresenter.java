package com.xinzu.xiaodou.pro.activity.cartype;

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
import com.xinzu.xiaodou.pro.activity.login.LoginContract;

import org.json.JSONObject;

import javax.inject.Inject;


public class CarTypePresenter extends BasePAV<CarTypeContract.View> implements CarTypeContract.Presenter {

    @Inject
    CarTypePresenter() {

    }


    @Override
    public void getcartype(String json, Context context) {
        MyApp.apiService(ApiService.class)
                .getCarGroups(RequestBodyUtil.RequestBody(json)
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
                    mView.getCarType(bean.string());
                }, throwable -> {
                    com.radish.baselibrary.utils.LogUtils.e("联网失败：" + throwable.toString());
                });
//        mView.showLoading();
//        new Thread(() -> {
//            OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
//            okHttpRequestUtils.requestAsynjson(ApiService.getCarGroups, json, new RequestCallBack() {
//                @Override
//                public void onRequestSuccess(Object result) {
//                    LogUtils.e(result.toString());
//                    mView.getCarType(result.toString());
//
//                }
//
//                @Override
//                public void onRequestFailed(String errorMsg) {
//
//                }
//            });
//        }).start();
    }

    @Override
    public void getcar(String json, Context context) {
        MyApp.apiService(ApiService.class)
                .searchVehicle(RequestBodyUtil.RequestBody(json)
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
                    mView.getCar(bean.string());
                }, throwable -> {
                    com.radish.baselibrary.utils.LogUtils.e("联网失败：" + throwable.toString());
                });

//            OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
//            okHttpRequestUtils.requestAsynjson(ApiService.searchVehicle, json, new RequestCallBack() {
//                @Override
//                public void onRequestSuccess(Object result) {
//                    LogUtils.e(result.toString());
//                    mView.getCar(result.toString());
//                }
//
//                @Override
//                public void onRequestFailed(String errorMsg) {
//
//                }
//            });
//        }).start();
    }
}
