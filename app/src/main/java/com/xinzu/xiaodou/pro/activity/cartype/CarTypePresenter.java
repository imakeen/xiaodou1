package com.xinzu.xiaodou.pro.activity.cartype;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.pro.activity.login.LoginContract;

import javax.inject.Inject;


public class CarTypePresenter extends BasePAV<CarTypeContract.View> implements CarTypeContract.Presenter {

    @Inject
    CarTypePresenter() {

    }


    @Override
    public void getcartype(String json, Context context) {
        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
        okHttpRequestUtils.requestAsynjson(ApiService.getCarGroups, json, new RequestCallBack() {
            @Override
            public void onRequestSuccess(Object result) {
                LogUtils.e(result.toString());
                mView.getCarType(result.toString());

            }

            @Override
            public void onRequestFailed(String errorMsg) {

            }
        });
    }

    @Override
    public void getcar(String json, Context context) {
        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(context);
        okHttpRequestUtils.requestAsynjson(ApiService.searchVehicle, json, new RequestCallBack() {
            @Override
            public void onRequestSuccess(Object result) {
                LogUtils.e(result.toString());
                mView.getCar(result.toString());

            }

            @Override
            public void onRequestFailed(String errorMsg) {

            }
        });
    }
}
