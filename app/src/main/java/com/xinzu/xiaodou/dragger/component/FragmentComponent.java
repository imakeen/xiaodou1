package com.xinzu.xiaodou.dragger.component;

import android.app.Activity;

import com.xinzu.xiaodou.dragger.FragmentScope;
import com.xinzu.xiaodou.dragger.module.FragmentModule;
import com.xinzu.xiaodou.pro.fragment.home.HomeFragment;
import com.xinzu.xiaodou.pro.fragment.mine.MineFragment;
import com.xinzu.xiaodou.pro.fragment.order.OrderFragment;

import dagger.Component;

/**
 * @author Administrator
 */
@FragmentScope
@Component(dependencies = MyAppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment fragment);


    void inject(OrderFragment fragment);

    void inject(MineFragment fragment);
}
