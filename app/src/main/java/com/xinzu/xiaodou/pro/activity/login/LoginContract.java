package com.xinzu.xiaodou.pro.activity.login;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

import okhttp3.ResponseBody;


public interface LoginContract {


    interface View extends BaseView {

    }

    interface Presenter extends BasePersenter<View> {

    }

}
