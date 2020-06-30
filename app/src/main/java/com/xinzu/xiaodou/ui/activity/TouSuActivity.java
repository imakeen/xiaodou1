package com.xinzu.xiaodou.ui.activity;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.Intent.IntentData;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.bean.OrPriceDetailBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.ui.adapter.FeiyongAdapter;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TouSuActivity extends BaseGActivity {
    @IntentData
    String orderCode="";
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_opinion;
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
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvNum.setText(editable.length() + "/200");
            }
        });
    }

    @OnClick({R.id.bt_submit, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                Tousu();
                break;
            case R.id.back:
                finish();
                break;
        }


    }

    private void Tousu() {
        if (etContent.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入投诉内容");
            return;
        }
        Hashtable<String, Object> hashMap = new Hashtable<>();
        hashMap.put("appKey", ApiService.appKey);
        String temp=SignUtils.temp();
        hashMap.put("timeStamp", temp);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("orderCode", orderCode);
        hashMap.put("complaintcontent ", etContent.getText().toString());
        hashMap.put("userId ", SPUtils.getInstance().getString("userid"));
        Date date = new Date(System.currentTimeMillis());
        hashMap.put("complainttime ", date);
        hashMap.put("channelId  ", "4");
        MyApp.apiService(ApiService.class).complaint(
                RequestBodyUtil.RequestBody(new Gson().toJson(hashMap))
        ).compose(RxSchedulers.io_main())
                .doOnSubscribe(d -> {
                    showLoading();
                })
                .doFinally(() -> {
                    closeLoading();
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new SuccessfulConsumer() {
                    @Override
                    public void success(String result) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if (1 == jsonObject.getInt("status")) {
                                ToastUtil.showShort("投诉成功");
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
}
