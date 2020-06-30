package com.xinzu.xiaodou.ui.activity;

import android.view.View;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;

import butterknife.OnClick;

public class YndingXuzhiActicity extends BaseGActivity {
    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.actitvity_tongzhi;
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

    @OnClick({R.id.back})
    public void onClick(View view) {

        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
