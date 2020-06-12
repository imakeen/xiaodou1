package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.view.View;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.radish.baselibrary.utils.LogUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.OnClick;

/**
 * 添加驾驶员
 *
 * @parm
 * @return
 */
public class NewUserActivity extends BaseGActivity {
    private OptionsPickerView pvOptions;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_new_car_user;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private void carType() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        MyApp.apiService(ApiService.class)
                .getCardType(RequestBodyUtil.jsonRequestBody(hashMap)).compose(RxSchedulers.io_main())
                .doOnSubscribe(disposable -> {
                    showLoading();
                }).doFinally(() -> {
            showLoading();
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(bean -> {
                    JSONObject jsonObject = new JSONObject(bean.string());
                    com.blankj.utilcode.util.LogUtils.e(jsonObject.toString());
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                    onFail();
                });
    }

    private void submitUser() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        MyApp.apiService(ApiService.class)
                .editConsumers(RequestBodyUtil.jsonRequestBody(hashMap)).compose(RxSchedulers.io_main())
                .doOnSubscribe(disposable -> {
                    showLoading();
                }).doFinally(() -> {
            showLoading();
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(bean -> {
                    JSONObject jsonObject = new JSONObject(bean.string());
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                    onFail();
                });
    }

    @OnClick({R.id.bt_submit_user, R.id.tv_zj_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit_user:
                submitUser();
                break;
            case R.id.tv_zj_type:
                carType();
                break;
        }
    }
}
