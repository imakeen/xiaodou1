package com.radish.baselibrary.ioc;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者 radish
 * @创建日期 2018/11/17 4:06 PM
 * @邮箱 15703379121@163.com
 * @描述 View事件的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    @IdRes int[] value()default {View.NO_ID};
}
