package com.xinzu.xiaodou.base.mvp;



import com.xinzu.xiaodou.dragger.component.ActivityComponent;
import com.xinzu.xiaodou.dragger.component.DaggerActivityComponent;
import com.xinzu.xiaodou.dragger.module.ActivityModule;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.dragger.component.ActivityComponent;
import com.xinzu.xiaodou.dragger.module.ActivityModule;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVP BaseMvpActivity
 *
 * @author hzy
 */
public abstract class BaseMvpActivity<T extends BasePersenter> extends BaseGActivity {


    @Inject //dragger
    @Nullable
    public T mPresenter;

    @Override
    protected void initLayoutAfter() {
        super.initLayoutAfter();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .myAppComponent(MyApp.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
