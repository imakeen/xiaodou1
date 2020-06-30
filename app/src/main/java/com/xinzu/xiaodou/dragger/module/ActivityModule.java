package com.xinzu.xiaodou.dragger.module;

import android.app.Activity;

import com.xinzu.xiaodou.dragger.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class ActivityModule {
    private  Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
