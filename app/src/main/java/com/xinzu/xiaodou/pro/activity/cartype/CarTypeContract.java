package com.xinzu.xiaodou.pro.activity.cartype;

import android.content.Context;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

/**
 * Created by hzy on 2019/1/18
 * LoginContract
 *
 * @author Administrator
 */
public interface CarTypeContract {


    interface View extends BaseView {
        void getCarType(String cattype);

        void getCar(String car);

    }

    interface Presenter extends BasePersenter<View> {
        void getcartype(String json, Context context);

        void getcar(String json, Context context);
    }

}
