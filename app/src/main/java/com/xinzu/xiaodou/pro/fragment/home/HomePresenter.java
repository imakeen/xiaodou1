package com.xinzu.xiaodou.pro.fragment.home;

import android.arch.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class HomePresenter extends BasePAV<HomeContract.View> implements HomeContract.Presenter {
    @Inject
    public HomePresenter() {

    }


    @Override
    public void update(String json) {
        MyApp.apiService(ApiService.class)
                .versionUp(RequestBodyUtil.RequestBody(json)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {

                })
                .doFinally(() -> {

                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String jsonObject) {
                        com.blankj.utilcode.util.LogUtils.e(jsonObject);
                        mView.update(jsonObject);
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }
}
