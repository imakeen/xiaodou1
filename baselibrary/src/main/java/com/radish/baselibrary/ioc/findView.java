package com.radish.baselibrary.ioc;

import android.support.annotation.IdRes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者 radish
 * @创建日期 2018/11/17 3:04 PM
 * @邮箱 15703379121@163.com
 * @描述
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface findView {
    @IdRes int value();
}
