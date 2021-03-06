package com.xinzu.xiaodou.pro.activity.city;

import android.content.Context;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

/**
 * Created by hzy on 2019/1/18
 * LoginContract
 *
 * @author Administrator
 */
public interface CityPickContract {


    interface View extends BaseView {
        void getCity(String city);
    }

    interface Presenter extends BasePersenter<View> {
        void getCity(String appKey, String timeStamp, String sign, Context context);
    }

}
