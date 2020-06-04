package com.xinzu.xiaodou.pro.fragment.home;

import android.arch.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.mvp.BasePAV;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class HomePresenter extends BasePAV<HomeContract.View> implements HomeContract.Presenter {
    @Inject
    public HomePresenter() {

    }


}
