package com.radish.baselibrary.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.radish.baselibrary.utils.NetUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @作者 radish
 * @创建日期 2018/11/17 3:12 PM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class ViewUtils {

    // Activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }


    // Fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    // View
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    // 兼容 上面三个方法  object->反射需要执行的类
    private static void inject(ViewFinder finder, Object object) {
        //注入属性
        injectFiled(finder, object);
        //注入事件
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
//        1.过去类里面的所有属性
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

//        2.获取viewById的value值
        for (Field field : fields) {
            findView findView = field.getAnnotation(findView.class);
            if (findView != null) {
                //有注解 且获取View的Id
                int viewId = findView.value();

//              3.findViewById找到View
                View view = finder.findViewById(viewId);

                if (view != null) {
                    // 能够注入所有修饰符
                    field.setAccessible(true);

//                    4.动态注入找到的View
                    try {
                        // filed.set()->给对象object里的这个属性设置值view。
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    private static void injectEvent(ViewFinder finder, Object object) {
//        1.过去类里的所有方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
//                2.获取OnClick里的value值
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
//                    3.findViewById 找到View
                    View view = finder.findViewById(viewId);
                    if (view != null) {
//                    4.设置事件监听
                        CheckNet checkNet = method.getAnnotation(CheckNet.class);
                        if (checkNet == null)
                            view.setOnClickListener(new InjectOnClickListener(method, object, false));
                        else
                            view.setOnClickListener(new InjectOnClickListener(method, object, true));
                    }
                }
            }
        }
    }


    private static class InjectOnClickListener implements View.OnClickListener {

        private final Method mMethod;
        private final Object mObject;
        private final boolean mIsCheckNet;

        InjectOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            if (mIsCheckNet && !NetUtils.isNetAvailable(v.getContext())) {
                Toast.makeText(v.getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                return;
            }
//                处理所有方法，无论私有公有等修饰
            mMethod.setAccessible(true);
            try {
                Class<?>[] parameterTypes = mMethod.getParameterTypes();
//                5.反射执行方法
                if (parameterTypes == null || parameterTypes.length == 0) {

                    mMethod.invoke(mObject);
                } else if (parameterTypes.length == 1 && parameterTypes[0].getName().contains("android.view.")){
                    mMethod.invoke(mObject, v);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}


