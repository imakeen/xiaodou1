package com.xinzu.xiaodou.dragger.component;

import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.dragger.ContextLife;
import com.xinzu.xiaodou.dragger.module.MyAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 */
@Singleton
@Component(modules = MyAppModule.class)
public interface MyAppComponent {

    /**
     * 提供App的Context
     *
     * @return
     */
    @ContextLife("Application")
    MyApp getContext();

}
