package com.xinzu.xiaodou.pro.activity.login;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

import okhttp3.ResponseBody;

/**
 * Created by hzy on 2019/1/18
 * LoginContract
 *
 * @author Administrator
 * */
public interface LoginContract {


    interface View extends BaseView {

    }

    interface Presenter extends BasePersenter<View> {

    }

}
