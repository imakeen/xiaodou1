package com.xinzu.xiaodou.pro.fragment.order;

import android.arch.lifecycle.LifecycleOwner;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.fragment.home.HomeContract;
import com.xinzu.xiaodou.ui.activity.CarInfoActivity;
import com.xinzu.xiaodou.wxapi.AliPay;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class OrderPresenter extends BasePAV<OrderContract.View> implements OrderContract.Presenter {
    @Inject
    public OrderPresenter() {

    }


    @Override
    public void userOrderList(String bean) {
        MyApp.apiService(ApiService.class)
                .userOrderList(RequestBodyUtil.RequestBody(bean)
                )
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
                        com.blankj.utilcode.util.LogUtils.e(jsonObject);
                        mView.getOrderList(jsonObject);
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }

    @Override
    public void userOrderDetails(String bean) {
        MyApp.apiService(ApiService.class)
                .orderDetail(RequestBodyUtil.RequestBody(bean)
                )
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
                        com.blankj.utilcode.util.LogUtils.e(jsonObject);
                        mView.getOrderDetails(jsonObject);
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }
}
