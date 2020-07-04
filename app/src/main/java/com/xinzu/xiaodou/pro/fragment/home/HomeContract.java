package com.xinzu.xiaodou.pro.fragment.home;

import com.xinzu.xiaodou.base.mvp.BasePersenter;
import com.xinzu.xiaodou.base.mvp.BaseView;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public interface HomeContract {
    // update UI
    interface View extends BaseView {
        void update(String body);
    }

    // 连接 数据
    interface Presenter extends BasePersenter<View> {
        void update(String json);
    }
}
