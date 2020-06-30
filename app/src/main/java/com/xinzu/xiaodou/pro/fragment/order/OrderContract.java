package com.xinzu.xiaodou.pro.fragment.order;

import android.content.Context;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public interface OrderContract {
    // update UI
    interface View extends BaseView {
        void getOrderList(String body);

        void getOrderDetails(String body);
    }

    // 连接 数据
    interface Presenter extends BasePersenter<View> {
        void userOrderList(String bean , Context context);

        void userOrderDetails(String bean,Context context);
    }
}
