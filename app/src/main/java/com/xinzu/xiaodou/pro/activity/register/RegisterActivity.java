package com.xinzu.xiaodou.pro.activity.register;


import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;
import com.xinzu.xiaodou.util.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/6/10 09:08
 */
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.ed_code)
    EditText et_code;//验证码

    @BindView(R.id.ed_phone)
    EditText et_phone;//手机号

    @BindView(R.id.ed_password)
    EditText et_password;//密码

    @BindView(R.id.bt_getcode)
    Button bt_getcode;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setStatusBarColor();
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

    @OnClick({R.id.bt_getcode, R.id.bt_regist, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_getcode:
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobilePhoneNumber(et_phone.getText().toString())) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                mPresenter.getsendRegistSms(et_phone.getText().toString(), RegisterActivity.this);
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        bt_getcode.setText(millisUntilFinished / 1000 + "秒");
                        bt_getcode.setEnabled(false);
                    }

                    @Override
                    public void onFinish() {
                        bt_getcode.setText("点击重新获取");
                        bt_getcode.setEnabled(true);
                    }
                }.start();
                break;
            case R.id.bt_regist:
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                String phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                String code = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                String password = et_password.getText().toString().trim();
                //  mPresenter.getregister(phone, password, inviteCode, code);

                break;
            case R.id.back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
        }
    }
}
