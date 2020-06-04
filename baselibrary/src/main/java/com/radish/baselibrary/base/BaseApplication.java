package com.radish.baselibrary.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * @创建日期 2018/11/24 11:16 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public abstract class BaseApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        CrashHandler.getInstance().init(this, "上传错误日志的url");

        init();
    }

    protected abstract void init();


    protected static BaseApplication mInstance;

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

}
