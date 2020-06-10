package com.xinzu.xiaodou.pro.activity.login;


import android.view.View;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;

import butterknife.OnClick;

/**
 * Created by hzy on 2019/1/18
 * LoginActivity  登录界面
 *
 * @author hzy
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }





    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
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
}
