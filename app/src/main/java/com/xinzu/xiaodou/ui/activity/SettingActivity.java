package com.xinzu.xiaodou.ui.activity;

import android.Manifest;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.radish.baselibrary.dialog.OnDialogViewClickListener;
import com.radish.baselibrary.dialog.RadishDialog;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.base.OnPermissionCallBack;
import com.xinzu.xiaodou.util.CommonOperate;

import butterknife.OnClick;

public class SettingActivity extends BaseGActivity {
    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
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

    @OnClick({R.id.my_opinion, R.id.back, R.id.lianxi
            , R.id.ll_about
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_opinion:
                ActivityUtils.startActivity(OpinionActivity.class);
                break;

            case R.id.back:
                finish();
                break;


            case R.id.lianxi:
                break;
            case R.id.ll_about:
                ActivityUtils.startActivity(AboutActivity.class);
                break;

        }
    }

}
