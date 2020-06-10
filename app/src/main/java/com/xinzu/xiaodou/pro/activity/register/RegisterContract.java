package com.xinzu.xiaodou.pro.activity.register;

import android.content.Context;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;


public interface RegisterContract {


    interface View extends BaseView {

    }

    interface Presenter extends BasePersenter<View> {
        void getsendRegistSms(String mobile, Context context);
    }

}
