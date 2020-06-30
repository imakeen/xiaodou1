package com.xinzu.xiaodou.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.radish.baselibrary.utils.LogUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RequestBodyUtil;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.pro.activity.registerlogin.RegisterActivity;
import com.xinzu.xiaodou.util.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseGActivity {
    @BindView(R.id.tv_html)
    TextView tvHtml;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_jianjie;
    }

    @Override
    protected void initView() {
        String exchange = getResources().getString(R.string.jieshao_5);
        tvHtml.setText(Html.fromHtml(exchange));
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


    @OnClick({R.id.back, R.id.tv_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_tuichu:
                DialogUtil.showAlterDialog(this, "确认退出？", (dialog, view1) -> {
                    SPUtils.getInstance().clear();
                    ActivityUtils.startActivity(RegisterActivity.class);
                    finish();
                });
        }

    }

}
