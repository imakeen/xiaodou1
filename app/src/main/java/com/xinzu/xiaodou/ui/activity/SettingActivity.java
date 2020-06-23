package com.xinzu.xiaodou.ui.activity;

import android.Manifest;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.radish.baselibrary.dialog.OnDialogViewClickListener;
import com.radish.baselibrary.dialog.RadishDialog;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.base.OnPermissionCallBack;
import com.xinzu.xiaodou.pro.fragment.mine.MineFragment;
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

    @OnClick({R.id.my_opinion, R.id.back, R.id.lianxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_opinion:
                ActivityUtils.startActivity(OpinionActivity.class);
                break;

            case R.id.back:
                finish();
                break;


            case R.id.lianxi:
                callphone();
                break;
        }
    }

    private void callphone() {
        String phone = "400-6767-388";
        getPermission(new OnPermissionCallBack() {
            @Override
            public void permissionPass(String[] permissions) {
                new RadishDialog.Builder(SettingActivity.this).setView(R.layout.dialog_alter_pink)
                        .setText(R.id.dialog_message, "即将拨打电话" + phone)
                        .setText(R.id.dialog_submit, "立即拨打")
                        .setText(R.id.dialog_cancel, "取消拨打")
                        .setClick(R.id.dialog_submit, new OnDialogViewClickListener() {
                            @Override
                            public void onClick(final RadishDialog dialog, View view) {
                                CommonOperate.callPhone(SettingActivity.this, phone);
                                dialog.dismiss();

                            }
                        })
                        .setClick(R.id.dialog_cancel, new OnDialogViewClickListener() {
                            @Override
                            public void onClick(RadishDialog dialog, View view) {
                                dialog.dismiss();
                            }
                        }).show();
            }

            @Override
            public void permissionRefuse(String[] permissions) {

            }
        }, Manifest.permission.CALL_PHONE);
    }
}
