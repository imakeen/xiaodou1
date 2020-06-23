package com.xinzu.xiaodou.ui.activity;


import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.bean.CarUserBean;
import com.xinzu.xiaodou.bean.CreatOrderBean;
import com.xinzu.xiaodou.bean.SuccessOrderBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.util.GlideUtils;
import com.xinzu.xiaodou.util.SignUtils;
import com.xinzu.xiaodou.wxapi.AliPay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/6/10 11:16
 */
public class CarInfoActivity extends BaseGActivity {

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
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_edit_user)
    TextView tvEditUser;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_money)
    TextView tv_money;
    private CarBean.StoreListBean bean;
    private String cityinfo;
    private String day;
    private String city;
    private String picktime;
    private String citywide;
    private String returntime;
    private CreatOrderBean creatOrderBean = new CreatOrderBean();
    private CreatOrderBean.UserInfoBean userInfoBean;
    private CarUserBean.ConsumersBean consumersBean;
    private SuccessOrderBean successOrderBean;
    ;

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        bean = bundle.getParcelable("carlist");
        cityinfo = bundle.getString("cityinfo");
        day = bundle.getString("day");
        city = bundle.getString("city");
        picktime = bundle.getString("picktime");
        returntime = bundle.getString("returntime");
        citywide = bundle.getString("citywide");
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initView() {
        GlideUtils.getInstance().loadPathImage(this, bean.getImage(), carImage);
        tvVehicleName.setText(bean.getVehicleName());
        tvDisplacement.setText(bean.getDisplacement());
        tvPickCity.setText(city);
        tvPickTime.setText(picktime);
        tvReturnTime.setText(returntime);
        tvReturnCity.setText(city);
        tvPickCityInfo.setText(cityinfo);
        tvReturnCityInfo.setText(cityinfo);
        tv_money.setText("￥" + bean.getAmount() + "元");
        tvDay.setText(day);
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

    @OnClick({R.id.ll_users, R.id.tv_yuding,
            R.id.bt_usercar, R.id.tv_feiyong, R.id.back})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_usercar:
                if (tvUser.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请选择司机");
                    return;
                }
                creatOrderBean.setAppKey(ApiService.appKey);
                creatOrderBean.setChannelId(4);
                creatOrderBean.setOrderChannel(1);
                creatOrderBean.setOrderSource(1);
                creatOrderBean.setPayAmount(bean.getAmount() + "");
                creatOrderBean.setPayMode(2);
                creatOrderBean.setPriceType(1);
                creatOrderBean.setAddress(cityinfo);
                CreatOrderBean.PickoffOndoorAddrBean pickoffOndoorAddrBean = new CreatOrderBean.PickoffOndoorAddrBean();
                creatOrderBean.setPickoffOndoorAddr(pickoffOndoorAddrBean);
                CreatOrderBean.PickupOndoorAddrBean pickupOndoorAddrBean = new CreatOrderBean.PickupOndoorAddrBean();
                creatOrderBean.setPickupOndoorAddr(pickupOndoorAddrBean);
                pickoffOndoorAddrBean.setAddress(cityinfo);
                creatOrderBean.getPickupOndoorAddr().setAddress(cityinfo);
                creatOrderBean.setPickupCityCode(citywide);
                creatOrderBean.setPickupDate(picktime);
                creatOrderBean.setPickupStoreCode(bean.getPickupStoreCode());
                creatOrderBean.setReturnCityCode(citywide);
                creatOrderBean.setReturnDate(returntime);
                creatOrderBean.setReturnStoreCode(bean.getReturnStoreCode());
                creatOrderBean.setSign(SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
                creatOrderBean.setTimeStamp(SignUtils.temp());
                creatOrderBean.setUserId(SPUtils.getInstance().getString("userid"));
                creatOrderBean.setVehicleCode(bean.getVehicleCode());
                creatOrderBean.setTotalDays(day);
                creatOrderBean.setUserInfo(userInfoBean);

                CreatOrder(creatOrderBean);
                break;
            case R.id.ll_users:
                IntentUtils.getInstance()
                        .with(CarInfoActivity.this, CaruserActivity.class)
                        .putInt("type", 1)
                        .start(22);
                break;

            case R.id.tv_feiyong:

                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_yuding:
                ActivityUtils.startActivity(YndingXuzhiActicity.class);
                break;
        }
    }


    private void CreatOrder(CreatOrderBean orderBean) {
        Gson gson = new Gson();
        MyApp.apiService(ApiService.class)
                .createOrder(RequestBodyUtil.RequestBody(gson.toJson(orderBean))
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
                                successOrderBean = new Gson().fromJson(jsonObject, SuccessOrderBean.class);
                                AppPay(successOrderBean.getOrderCode(), successOrderBean.getPayAmount());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, throwable -> {
                    LogUtils.e("联网失败：" + throwable.toString());
                });

    }

    private void AppPay(String OddNumbers, int price) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderCodel", OddNumbers);
        hashMap.put("tradeNo", OddNumbers);
        hashMap.put("price", price + "");
        MyApp.apiService(ApiService.class)
                .AppPay(RequestBodyUtil.jsonRequestBody(hashMap)
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
                            switch (object.getInt("status")) {
                                case 1:
                                    AliPay.pay(CarInfoActivity.this, successOrderBean.getOrderCode(), String.valueOf(successOrderBean.getPayAmount()), () -> {
                                        ActivityCollector.finishAll();
                                        IntentUtils.getInstance().with(CarInfoActivity.this, MainActivity.class).putInt("order", 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            // 地址列表 MyDizhi
            assert data != null;
            consumersBean = data.getParcelableExtra("consumersBean");
            userInfoBean = new CreatOrderBean.UserInfoBean();
            userInfoBean.setIdNo(consumersBean.getIdNo());
            userInfoBean.setIdType(consumersBean.getType());
            userInfoBean.setMobile(consumersBean.getMobile());
            userInfoBean.setName(consumersBean.getUserName());
            tvUser.setText(consumersBean.getUserName());

        } else if (requestCode == 101) {
        }
    }
}
