package com.xinzu.xiaodou.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.Intent.IntentData;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.ActivityCollector;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.bean.OrPriceDetailBean;
import com.xinzu.xiaodou.bean.OrderdetailsBean;
import com.xinzu.xiaodou.bean.SuccessOrderBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.ui.adapter.FeiyongAdapter;
import com.xinzu.xiaodou.util.DialogUtil;
import com.xinzu.xiaodou.util.GlideUtils;
import com.xinzu.xiaodou.util.SignUtils;
import com.xinzu.xiaodou.wxapi.AliPay;

import org.apache.catalina.util.ToStringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseGActivity {
    @IntentData
    OrderdetailsBean bean = null;
    @IntentData
    String orderid = "";
    @BindView(R.id.car_image)
    ImageView carImage;
    @BindView(R.id.tv_vehicleName)
    TextView tvVehicleName;
    @BindView(R.id.tv_displacement)
    TextView tvDisplacement;
    @BindView(R.id.tv_pick_time)
    TextView tvPickTime;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_return_time)
    TextView tvReturnTime;
    @BindView(R.id.tv_pick_city)
    TextView tvPickCity;
    @BindView(R.id.tv_pick_city_info)
    TextView tvPickCityInfo;
    @BindView(R.id.tv_return_city)
    TextView tvReturnCity;
    @BindView(R.id.tv_return_city_info)
    TextView tvReturnCityInfo;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_paytype)
    TextView tvPaytype;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.bt_usercar)
    Button btUsercar;
    @BindView(R.id.tv_quxiao)
    TextView tv_quxiao;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_order_info;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        if (bean != null) {
            tvVehicleName.setText(bean.getVehicleName());
            tvPickTime.setText(bean.getPickupDate());
            tvDay.setText(bean.getTotalDays());
            tvReturnTime.setText(bean.getReturnDate());
            tvPickCity.setText(bean.getPickupCityName());
            tvPickCityInfo.setText(bean.getPickupAddress());
            tvReturnCity.setText(bean.getReturnCityName());
            tvReturnCityInfo.setText(bean.getReturnAddress());
            tvMoney.setText("￥" + bean.getPayMount() + "元");
            tvDisplacement.setText(bean.getDisplacement());
            tvUser.setText(bean.getDriverName());
            tvPhone.setText(bean.getPhone());
            tvCard.setText(bean.getIdNo());
            GlideUtils.getInstance().loadPathImage(OrderDetailsActivity.this, bean.getImage(), carImage);

            if (bean.getPayments() == 0) {
                if (bean.getOrderState() == 3) {
                    btUsercar.setText("删除订单");
                    tv_quxiao.setVisibility(View.GONE);
                } else {
                    btUsercar.setText("支付订单");
                    tv_quxiao.setText("取消订单");
                }
            } else if (bean.getPayments() == 1) {
                switch (bean.getOrderState()) {
                    case 0:
                    case 2:
                        tv_quxiao.setVisibility(View.GONE);
                        btUsercar.setText("取消订单");

                        break;
                    case 6:
                        tv_quxiao.setVisibility(View.GONE);
                        btUsercar.setVisibility(View.GONE);
                        break;
                    case 1:
                        btUsercar.setText("删除订单");
                        tv_quxiao.setText("意见反馈");
                        break;
                    case 3:
                        btUsercar.setText("删除订单");
                        tv_quxiao.setVisibility(View.GONE);
                        break;

                }
            }
        }
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


    @OnClick({R.id.tv_youhui, R.id.bt_usercar, R.id.tv_quxiao, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_youhui:
                feiyong();
                break;
            case R.id.bt_usercar:
                if (bean.getPayments() == 0) {
                    if (bean.getOrderState() == 3) {
                        delete();
                    } else {
                        AppPay(orderid, bean.getPayMount());
                    }
                } else {
                    switch (bean.getOrderState()) {
                        case 0:
                        case 2:
                            cancls();
                            break;
                        case 3:
                        case 1:
                            delete();
                            break;

                    }
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_quxiao:
                if (bean.getPayments() == 0) {
                    showLoading();
                    cancl();
                } else {
                    switch (bean.getOrderState()) {
                        case 1:
                            IntentUtils.getInstance()
                                    .with(OrderDetailsActivity.this
                                            , TouSuActivity.class).
                                    putString("orderCode", orderid).
                                    start();
                            break;
                    }
                }
                break;
        }
    }

    private void AppPay(String OddNumbers, int price) {

        Hashtable<String, String> hashMap = new Hashtable<>();
        hashMap.put("orderCodel", OddNumbers);
        hashMap.put("tradeNo", OddNumbers);
        hashMap.put("price", price + "");
        MyApp.apiService(ApiService.class)
                .AppPay(RequestBodyUtil.hashtableRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {

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
                            LogUtils.e(object.getInt("status") + "");
                            switch (object.getInt("status")) {
                                case 1:
                                    AliPay.pay(OrderDetailsActivity.this, orderid, String.valueOf(price), () -> {
                                        IntentUtils.getInstance().with(OrderDetailsActivity.this, MainActivity.class).putInt("order", 1);
                                        Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);
                                        intent.putExtra("order", 1);
                                        startActivity(intent);
                                    });
                                    break;
                                case 0:
                                case -1:
                                    ToastUtil.showShort(object.getString("message"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });

    }

    private void refundApp(String refundAmount) {
        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        hashMap.put("outTradeNo", orderid);
        hashMap.put("refundAmount", "0.01");
        hashMap.put("appKey", "xzcxzfb");
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("channelId", "4");
        hashMap.put("orderChannel", "1");
        MyApp.apiService(ApiService.class)
                .refundApp(RequestBodyUtil.hashtableRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {

                })
                .doFinally(() -> {
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String jsonObject) {
                        com.blankj.utilcode.util.LogUtils.e(jsonObject);
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject);
                            if (jsonObject1.getInt("status") == 1) {
                                cancl();
                            }
                            ToastUtil.showShort(jsonObject1.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }

    private void cancls() {
        showLoading();
        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        hashMap.put("orderCode", orderid);
        hashMap.put("appKey", "xzcxzfb");
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("channelId", "4");
        hashMap.put("orderChannel", "1");
        MyApp.apiService(ApiService.class)
                .cancleOrders(RequestBodyUtil.hashtableRequestBody(hashMap)
                )
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {

                })
                .doFinally(() -> {

                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String jsonObject) {
                        com.blankj.utilcode.util.LogUtils.e(jsonObject);
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject);
                            if (jsonObject1.getInt("status") == 1) {
                                refundApp(jsonObject1.getInt("deduction") + "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }

    private void cancl() {
        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        hashMap.put("orderCode", orderid);
        hashMap.put("appKey", "xzcxzfb");
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("channelId", "4");
        hashMap.put("orderChannel", "1");
        MyApp.apiService(ApiService.class)
                .cancleOrder(RequestBodyUtil.hashtableRequestBody(hashMap)
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
                            JSONObject jsonObject1 = new JSONObject(jsonObject);
                            if (jsonObject1.getInt("status") == 1) {
                                ToastUtil.showShort("订单取消成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });
    }

    private void delete() {
        DialogUtil.showAlterDialog(this, "确认删除？", (dialog, view) -> {
            Hashtable<String, String> hashMap = new Hashtable<>();
            hashMap.put("appKey", ApiService.appKey);
            String temp = SignUtils.temp();
            hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
            hashMap.put("timeStamp", temp);
            hashMap.put("orderCode", orderid);
            hashMap.put("channelId", "4");
            hashMap.put("orderChannel", "1");
            MyApp.apiService(ApiService.class)
                    .deleteOrder(RequestBodyUtil.hashtableRequestBody(hashMap)
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
                                JSONObject jsonObject1 = new JSONObject(jsonObject);
                                if (jsonObject1.getInt("status") == 1) {
                                    ToastUtil.showShort("订单删除成功");
                                    finish();
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

    private void feiyong() {
        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("channelId", "1");
        hashMap.put("orderCode", orderid);
        hashMap.put("orderChannel", "1");
        MyApp.apiService(ApiService.class)
                .getOrPriceDetail(RequestBodyUtil.hashtableRequestBody(hashMap)
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
                        OrPriceDetailBean orPriceDetailBean = new Gson().fromJson(jsonObject
                                , OrPriceDetailBean.class);
                        if (orPriceDetailBean.getStatus() == 1) {
                            final Dialog dialog = new Dialog(OrderDetailsActivity.this, R.style.DialogTheme);
                            View view = View.inflate(OrderDetailsActivity.this, R.layout.dialog_orderpay, null);
                            TextView tvPay = view.findViewById(R.id.tv_zcf);
                            TextView tv_zjc = view.findViewById(R.id.tv_zjc);
                            LinearLayout ll = view.findViewById(R.id.ll_all);
                            tv_zjc.setText("金额：" + orPriceDetailBean.getPayAmount());
                            tvPay.setText(orPriceDetailBean.getPriceInfo().getQuantity() + "*" + orPriceDetailBean.getPriceInfo().getStandardUnitPrice());
                            RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
                            FeiyongAdapter feiyongAdapter = new FeiyongAdapter();
                            feiyongAdapter.addData(orPriceDetailBean.getAddedServiceList());
                            feiyongAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(feiyongAdapter);
                            dialog.setContentView(view);
                            dialog.show();
                            if (dialog.isShowing()) {
                                ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }

                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });

    }
}
