package com.xinzu.xiaodou.base.mvp;

import com.blankj.utilcode.util.NetworkUtils;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.base.BaseGLFragment;
import com.xinzu.xiaodou.dragger.component.DaggerFragmentComponent;
import com.xinzu.xiaodou.dragger.component.FragmentComponent;
import com.xinzu.xiaodou.dragger.module.FragmentModule;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVP BaseMvpFragment
 *
 * @author Administrator
 *
 * */
public abstract class BaseMvpFragment<T extends BasePersenter> extends BaseGLFragment {
    @Inject
    @Nullable
    protected T mPresenter;

    protected abstract void initInject();

    @Override
    public void onResume() {
        super.onResume();
        if (!NetworkUtils.isConnected()) {
            onNetError();
        }
    }

    @Override
    protected void initLayoutAfter() {
        super.initLayoutAfter();
        initInject();
    }

    @Override
    protected void initViewCreated() {
        super.initViewCreated();
        mPresenter.attachView(this);
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .myAppComponent(MyApp.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

}
