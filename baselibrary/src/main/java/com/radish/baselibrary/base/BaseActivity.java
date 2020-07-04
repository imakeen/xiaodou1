package com.radish.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.radish.baselibrary.utils.ActivityCollector;


public abstract class BaseActivity extends AppCompatActivity {

    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSuperBefore();
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        initLayoutBefore();

        initBundle();

        //设置布局layout
        setContentView(initLayout());

        initLayoutAfter();

        initView();

        initTitle();

        initData();

        initListener();


    }

    protected void initSuperBefore() {

    }

    protected void initLayoutAfter() {

    }

    protected void initLayoutBefore() {

    }

    protected abstract void initBundle();

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initTitle();

    protected abstract void initData();

    protected abstract void initListener();

//    protected void startActivity(Class<?> clazz) {
//        intent = new Intent(this, clazz);
//        startActivity(intent);
//    }
//
//    protected void startActivity(Class<?> clazz, Bundle bundle, int requestCode) {
//        intent = new Intent(this, clazz);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, requestCode);
//    }

    protected <T extends View> T findView(@IdRes int viewId) {
        return findViewById(viewId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
