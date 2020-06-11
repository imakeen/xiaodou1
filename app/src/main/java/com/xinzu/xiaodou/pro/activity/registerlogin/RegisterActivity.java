package com.xinzu.xiaodou.pro.activity.registerlogin;


import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.util.RegexUtils;

import org.json.JSONException;
import org.json.JSONObject;

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


    @BindView(R.id.bt_getcode)
    Button bt_getcode;

    @BindView(R.id.bt_regist)
    Button bt_login;

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
        bt_login.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && et_code.getText().toString().length() > 0) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_phone.getText().toString().length() > 0 && count > 0) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                if (!RegexUtils.isMobilePhoneNumber(et_phone.getText().toString())) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                String phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                String code = et_code.getText().toString().trim();

                mPresenter.login(phone, code, this);

                break;
            case R.id.back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
        }
    }

    @Override
    public void getmsgcode(String result) {
        this.closeLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if ("1".equals(jsonObject.getString("status"))) {
                ToastUtil.showShort("短信发送成功");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getloginresult(String result) {
        this.closeLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (1 == jsonObject.getInt("status")) {
                ToastUtil.showShort("登录成功");
                SPUtils.getInstance().put("userid", jsonObject.getString("userId"));
                SPUtils.getInstance().put("phone", jsonObject.getString("phone"));
                ActivityUtils.startActivity(MainActivity.class);
            } else if (4 == jsonObject.getInt("status")) {
                ToastUtil.showShort("验证码错误");
            } else if (0 == jsonObject.getInt("status")) {
                ToastUtil.showShort("请发送验证码");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
