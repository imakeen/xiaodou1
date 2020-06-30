package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.radish.baselibrary.Intent.IntentData;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.bean.CarUserBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.ui.adapter.CarUserAdapter;
import com.xinzu.xiaodou.util.CommonUtil;
import com.xinzu.xiaodou.util.DialogUtil;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

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
    @BindView(R.id.sr2)
    SmartRefreshLayout smartRefreshLayout2;
    @BindView(R.id.ll_not_user)
    LinearLayout llNotUser;
    @BindView(R.id.ll_has_user)
    LinearLayout llHasUser;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private CarUserAdapter carUserAdapter;


    @IntentData
    private int type = 0;


    private ArrayList<CarUserBean.ConsumersBean> consumersBeanArrayList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


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
        CommonUtil.initRefresh(this, smartRefreshLayout2);
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
                getUserCar();
            }
        });
    }


    @OnClick({R.id.bt_new_user, R.id.bt_add_user, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_new_user:
            case R.id.bt_add_user:
                ActivityUtils.startActivity(NewUserActivity.class);
                break;
            case R.id.back:
                if (type == 1) {
                    if (consumersBeanArrayList == null || consumersBeanArrayList.size() == 0) {
                        IntentUtils.getInstance().with().putParcelable("bean", null)
                                .setResultAndFinish(this, 100);
                        return;
                    }
                }
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserCar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserCar();
    }

    private void getUserCar() {

        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp",temp);
        hashMap.put("userCode", SPUtils.getInstance().getString("userid"));
        hashMap.put("orderChannel", "1");

        MyApp.apiService(ApiService.class)
                .getConsumers(RequestBodyUtil.hashtableRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {
                    showLoading();
                })
                .doFinally(() -> {
                    closeLoading();
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new SuccessfulConsumer() {
                               @Override
                               public void success(String jsonObject) {
                                   LogUtils.e(jsonObject);
                                   CarUserBean carUserBean = new Gson().fromJson(jsonObject, CarUserBean.class);
                                   if (carUserBean.getConsumers().size() == 0 || carUserBean.getConsumers() == null) {
                                       smartRefreshLayout2.setVisibility(View.VISIBLE);
                                       smartRefreshLayout.setVisibility(View.GONE);
                                   } else {
                                       smartRefreshLayout2.setVisibility(View.GONE);
                                       smartRefreshLayout.setVisibility(View.VISIBLE);
                                       consumersBeanArrayList = new ArrayList<>();
                                       consumersBeanArrayList.addAll(carUserBean.getConsumers());
                                       carUserAdapter = new CarUserAdapter();
                                       carUserAdapter.setNewData(consumersBeanArrayList);
                                       carUserAdapter.notifyDataSetChanged();


                                       carUserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                           @Override
                                           public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                               switch (view.getId()) {
                                                   case R.id.iv_delcect:
                                                       deleteUser(consumersBeanArrayList.get(position).getConsumerId());
                                                       break;
                                                   case R.id.iv_edit:
                                                       Intent intent = new Intent(CaruserActivity.this, NewUserActivity.class);
                                                       Bundle bundle = new Bundle();
                                                       intent.putExtra("bundle", bundle);
                                                       bundle.putParcelable("bean", consumersBeanArrayList.get(position));
                                                       startActivity(intent);
                                                       break;
                                                   case R.id.ll_slecet_user:
                                                       if (type == 1) {
                                                           // 携带数据返回上一页面
                                                           IntentUtils.getInstance().with().putParcelable("consumersBean", carUserAdapter.getData().get(position))
                                                                   .setResultAndFinish(CaruserActivity.this, 100);
                                                       }
                                                       break;
                                               }
                                           }
                                       });
                                       recyclerView.setAdapter(carUserAdapter);

                                   }
                               }
                           }, throwable -> {
                            LogUtils.e("联网失败：" + throwable.toString());
                            // onFail();
                        }
                );

    }

    private void deleteUser(String ConsumerId) {
        Hashtable<String, String> hashMap = new Hashtable<>();
        hashMap.put("appKey", ApiService.appKey);
        String temp =  SignUtils.temp();
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp",temp);
        hashMap.put("userid", SPUtils.getInstance().getString("userid"));
        hashMap.put("consumerId", ConsumerId);
        DialogUtil.showAlterDialog(this, "确认删除？", (dialog, view) -> {
            MyApp.apiService(ApiService.class)
                    .deleteConsumers(RequestBodyUtil.hashtableRequestBody(hashMap)
                    )
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(d -> {
                        showLoading();
                    })
                    .doFinally(() -> {
                        closeLoading();
                    })
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                    .subscribe(new SuccessfulConsumer() {
                        @Override
                        public void success(String jsonObject) {
                            com.blankj.utilcode.util.LogUtils.e(jsonObject);
                            try {
                                JSONObject object = new JSONObject(jsonObject);
                                if (1 == object.getInt("status")) {
                                    if (ConsumerId.equals(SPUtils.getInstance().getString("ConsumerId"))) {
                                        SPUtils.getInstance().put("user", "");
                                        SPUtils.getInstance().put("ConsumerId", "");
                                    }

                                    ToastUtils.showShort(object.getString("message"));
                                    getUserCar();
                                } else {
                                    ToastUtils.showShort(object.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, throwable -> {
                        LogUtils.e("联网失败：" + throwable.toString());
                    });
        });
    }


}
