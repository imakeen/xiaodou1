package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.bean.CarUserBean;
import com.xinzu.xiaodou.bean.cardTypeBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加驾驶员
 *
 * @parm
 * @return
 */
public class NewUserActivity extends BaseGActivity {
    @BindView(R.id.et_show_name)
    EditText etShowName;
    @BindView(R.id.tv_zj_type)
    TextView tvZjType;
    @BindView(R.id.et_zj_code)
    EditText etZjCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.new_phone)
    LinearLayout newPhone;
    private OptionsPickerView pvOptions;
    private String type;
    private CarUserBean.ConsumersBean bean;

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null && !bundle.isEmpty()) {
            bean = bundle.getParcelable("bean");
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_new_car_user;
    }

    @Override
    protected void initView() {
        if (bean != null) {
            etShowName.setText(bean.getUserName());
            etZjCode.setText(bean.getIdNo());
            etPhone.setText(bean.getMobile());
            switch (bean.getType()) {
                case 1:
                    tvZjType.setText("身份证");
                    break;
                case 2:
                    tvZjType.setText("护照");
                    break;
                case 7:
                    tvZjType.setText("回乡证");
                    break;
                case 8:
                    tvZjType.setText("台胞证");
                    break;

            }
            type = String.valueOf(bean.getType());
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

    private void carType() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", "dbe41c5b394a2d47c0358bae016fb8f4");
        hashMap.put("timeStamp", "1591693086937");
        LogUtils.e(SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()) + "+++++++" + SignUtils.temp());
        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(this);
        okHttpRequestUtils.requestAsynjson("getCardType", new Gson().toJson(hashMap), new RequestCallBack() {
            @Override
            public void onRequestSuccess(Object result) {
                cardTypeBean cardTypeBean = new Gson().fromJson(result.toString(), cardTypeBean.class);
                List<cardTypeBean.CardTypeListBean> list = new ArrayList<>(cardTypeBean.getCardTypeList());
                initOptionPicker(list, tvZjType);
                pvOptions.show();
            }

            @Override
            public void onRequestFailed(String errorMsg) {

            }
        });
//        MyApp.apiService(ApiService.class)
//                .getCardType(RequestBodyUtil.jsonRequestBody(hashMap)
//                )
//                .compose(RxSchedulers.io_main())
//                .doOnSubscribe(d -> {
//                    showLoading();
//                })
//                .doFinally(() -> {
//                    closeLoading();
//                })
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
//                .subscribe(bean -> {
//                    cardTypeBean.CardTypeListBean cardTypeBean = new Gson().fromJson(bean.string(), cardTypeBean.CardTypeListBean.class);
//                    List<cardTypeBean.CardTypeListBean> list = new ArrayList<>();
//                    list.add(cardTypeBean);
//                    initOptionPicker(list, tvZjType);
//                    pvOptions.show();
//                }, throwable -> {
//                    LogUtils.e("联网失败：" + throwable.toString());
//                    onFail();
//                });
    }

    private void initOptionPicker(final List<cardTypeBean.CardTypeListBean> cardItem, final TextView view) {//条件选择器初始化
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getTypeName();
                type = String.valueOf(cardItem.get(options1).getType());
                view.setText(tx);
            }
        }).isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)//设置滚轮文字大小
                .setBackgroundId(0x55000000) //设置外部遮罩颜色
                .setDecorView(null)
                .setSubmitColor(R.color.colororange)
                .setCancelColor(R.color.colororange)
                .build();
        pvOptions.setPicker(cardItem);//

    }

    private void submitUser() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        hashMap.put("channelId", "4");
        hashMap.put("orderChannel", "1");
        hashMap.put("idNo", etZjCode.getText().toString());
        hashMap.put("phone", etPhone.getText().toString());
        hashMap.put("type", type);
        if (bean != null) {
            hashMap.put("consumerId", bean.getConsumerId());
        }
        hashMap.put("userName", etShowName.getText().toString());
        hashMap.put("userid", SPUtils.getInstance().getString("userid"));
        MyApp.apiService(ApiService.class)
                .editConsumers(RequestBodyUtil.jsonRequestBody(hashMap)).compose(RxSchedulers.io_main())
                .doOnSubscribe(disposable -> {
                    showLoading();
                }).doFinally(() -> {
            closeLoading();
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String jsonObject) {
                        LogUtils.e(jsonObject);
                        try {
                            JSONObject object = new JSONObject(jsonObject);
                            if (1 == object.getInt("status") || 0 == object.getInt("status")) {
                                ToastUtils.showShort(object.getString("message"));
                                finish();
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
    }

    @OnClick({R.id.bt_submit_user, R.id.tv_zj_type, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit_user:
                if (etShowName.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请输入姓名");
                    return;
                }
                if (etZjCode.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请输入证件号");
                    return;
                }
                if (etPhone.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请输入手机号");
                    return;
                }
                submitUser();
                break;
            case R.id.tv_zj_type:

                carType();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}


