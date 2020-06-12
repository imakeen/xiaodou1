package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.radish.baselibrary.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.util.CommonUtil;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择查看驾驶员页面
 *
 * @parm
 * @return
 */
public class CaruserActivity extends BaseGActivity {
    @BindView(R.id.srl)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_not_user)
    LinearLayout llNotUser;
    @BindView(R.id.ll_has_user)
    LinearLayout llHasUser;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_car_user;
    }

    @Override
    protected void initView() {
        getUserCar();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        RefreshLayout();
    }

    private void RefreshLayout() {
        CommonUtil.initRefresh(this, smartRefreshLayout);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1500);

            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1500);
            }
        });
    }


    @OnClick({R.id.bt_new_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_new_user:
                ActivityUtils.startActivity(NewUserActivity.class);
                break;
        }
    }

    private void getUserCar() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        hashMap.put("userCode", SPUtils.getInstance().getString("userid"));
        hashMap.put("orderChannel", "1");
        MyApp.apiService(ApiService.class)
                .getConsumers(RequestBodyUtil.jsonRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {
                    showLoading();
                })
                .doFinally(() -> {
                    closeLoading();
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(bean -> {
                    JSONObject jsonObject = new JSONObject(bean.string());
                    if (jsonObject.get("consumers") == null) {
                        llNotUser.setVisibility(View.VISIBLE);
                        llHasUser.setVisibility(View.GONE);
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                    onFail();
                });
    }

}
