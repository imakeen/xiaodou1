package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.HttpManager;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 *
 * @parm
 * @return
 */
public class OpinionActivity extends BaseGActivity {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.bt_submit)
    Button btSubmit;

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

    @OnClick({R.id.back, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                if (!TextUtils.isEmpty(etContent.getText().toString())) {
                    submit();
                    return;
                }
                ToastUtil.showShort("请输入意见");
                break;
        }
    }

    private void submit() {
        Hashtable<String, String> hashMap = new Hashtable<>();
        String temp = SignUtils.temp();
        String sign = SignUtils.encodeSign("xzcxzfb" + "112233", temp);
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("appSecret", "112233");
        hashMap.put("timeStamp", temp);
        hashMap.put("addressType", "0");
        hashMap.put("isSecret", "0");
        hashMap.put("sign", sign);
        hashMap.put("content", etContent.getText().toString());
        showLoading();
        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(this);
        okHttpRequestUtils.requestAsynjson("saveFeedback", new Gson().toJson(hashMap)
                , new RequestCallBack() {
                    @Override
                    public void onRequestSuccess(Object result) {
                        closeLoading();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result.toString());
                            if (1 == jsonObject.getInt("status")) {
                                ToastUtil.showShort("提交成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onRequestFailed(String errorMsg) {
                        closeLoading();
                    }
                });
//        MyApp.apiService(ApiService.class)
//                .saveFeedback(RequestBodyUtil.hashtableRequestBody(hashMap)
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
//                    JSONObject jsonObject = new JSONObject(bean.string());
//                    if (1 == jsonObject.getInt("status")) {
//                        ToastUtil.showShort("提交成功");
//                        finish();
//                    }
//                }, throwable -> {
//                    LogUtils.e("联网失败：" + throwable.toString());
//                    onFail();
//                });
//        showLoading();
//        new Thread(() -> {
//
//            HashMap<String, String> hashMap = new HashMap<String, String>();
//            String sign = SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp());
//            hashMap.put("appKey", ApiService.appKey);
//            hashMap.put("appSecret", "112233");
//            hashMap.put("timeStamp", SignUtils.temp());
//            hashMap.put("addressType", "0");
//            hashMap.put("isSecret", "0");
//            hashMap.put("sign", sign);
//            hashMap.put("content", etContent.getText().toString());
//            runOnUiThread(() -> {
//                OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(this);
//                okHttpRequestUtils.requestAsynjson(ApiService.saveFeedback, new Gson().toJson(hashMap), new RequestCallBack() {
//                    @Override
//                    public void onRequestSuccess(Object result) {
//                        closeLoading();
//                        ToastUtil.showShort(result.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(result.toString());
//                            if (1 == jsonObject.getInt("status")) {
//                                ToastUtil.showShort("提交成功");
//                                finish();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onRequestFailed(String errorMsg) {
//                        closeLoading();
//                    }
//                });
//            });
//        }).start();
    }
}
