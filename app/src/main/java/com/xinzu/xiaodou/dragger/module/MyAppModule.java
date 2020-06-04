package com.xinzu.xiaodou.dragger.module;



import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.dragger.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class MyAppModule {
    private final MyApp application;

    public MyAppModule(MyApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    MyApp provideApplicationContext() {
        return application;
    }

}
