package com.xinzu.xiaodou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.http.OkHttpRequestUtils;
import com.xinzu.xiaodou.http.RequestCallBack;
import com.xinzu.xiaodou.util.RegexUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends BaseGActivity {
    @BindView(R.id.et_show_name)
    EditText etShowName;
    @BindView(R.id.et_show_gs)
    EditText etShowGs;
    @BindView(R.id.et_show_phone)
    EditText etShowPhone;
    @BindView(R.id.et_show_wx)
    EditText etShowWx;
    @BindView(R.id.et_show_city)
    EditText etShowCity;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void initBundle() {

    }



    @Override
    protected int initLayout() {
        return R.layout.activity_join;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

    private void join() {
//          `city` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
////  `companyName` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
////  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
////  `mobile` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
////  `weChat` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '微信',
////  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
////  `status` int(4) DEFAULT '1' COMMENT '1 未读 2已读',
        if (etShowName.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入您的姓名");
            return;
        }
        if (etShowGs.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入公司名称");
            return;
        }
        if (etShowPhone.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入电话");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(etShowPhone.getText().toString())) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (etShowWx.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入微信");
            return;
        }
        if (etShowCity.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入加盟城市");
            return;
        }
        if (etContent.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入内容");
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("city", etShowCity.getText().toString());
        hashMap.put("companyName", etShowGs.getText().toString());
        hashMap.put("name", etShowName.getText().toString());
        hashMap.put("mobile", etShowPhone.getText().toString());
        hashMap.put("weChat", etShowWx.getText().toString());
        hashMap.put("content", etContent.getText().toString());
        hashMap.put("status", "1");
        OkHttpRequestUtils okHttpRequestUtils = OkHttpRequestUtils.getInstance(this);
        okHttpRequestUtils.requestAsynjson(null, new Gson().toJson(hashMap), new RequestCallBack() {
            @Override
            public void onRequestSuccess(Object result) {
                LogUtils.e(result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    if (jsonObject.getInt("code") == 0) {
                        ToastUtil.showShort(jsonObject.getString("msg"));
                        finish();
                    } else {
                        ToastUtil.showShort(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                LogUtils.e(errorMsg.toString());
            }
        });
    }

    @OnClick({R.id.bt_submit, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                join();
                break;

            case R.id.back:
                finish();
                break;
        }
    }
}
