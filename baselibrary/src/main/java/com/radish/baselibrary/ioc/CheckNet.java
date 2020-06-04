package com.radish.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @作者 radish
 *
 * @创建日期 2018/11/17 11:00 AM
 *
 * @邮箱 15703379121@163.com
 *
 * 网络判断
 *
 */
// Runtime 运行时检查    Class 编译时检查     Source 源码资源时检查
@Retention(RetentionPolicy.RUNTIME)
// Field 注释只能放在属性上 Method 方法上  Constructor 构造方法上
@Target(ElementType.METHOD)
public @interface CheckNet {

}
