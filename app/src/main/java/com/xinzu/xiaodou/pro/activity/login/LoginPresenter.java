package com.xinzu.xiaodou.pro.activity.login;

import android.arch.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.utils.EncryptUtils;
import com.radish.baselibrary.utils.LogUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RxSchedulers;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class LoginPresenter extends BasePAV<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    LoginPresenter() {

    }


}
