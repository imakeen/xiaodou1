package com.xinzu.xiaodou.pro.activity.registerlogin;

import android.content.Context;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;


public interface RegisterContract {


    interface View extends BaseView {
        void getmsgcode(String result);

        void getloginresult(String result);

    }

    interface Presenter extends BasePersenter<View> {
        void getsendRegistSms(String mobile, Context context);

        void login(String login, String msgcode, Context context);
    }

}
