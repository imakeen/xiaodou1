package com.xinzu.xiaodou.dragger.component;

import android.app.Activity;

import com.xinzu.xiaodou.dragger.ActivityScope;
import com.xinzu.xiaodou.dragger.module.ActivityModule;
import com.xinzu.xiaodou.pro.activity.city.CityPickerActivity;
import com.xinzu.xiaodou.pro.activity.login.LoginActivity;

import dagger.Component;

/**
 * @author Administrator
 */
@ActivityScope
@Component(dependencies = MyAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity activity);

  void inject(CityPickerActivity activity);
}
