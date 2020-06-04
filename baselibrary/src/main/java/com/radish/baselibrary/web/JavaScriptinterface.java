package com.radish.baselibrary.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;

/**
 * @ 创建时间: 2017/12/7 on 11:29.
 * @ 描述：预留接口  通过js调用android原生方法
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class JavaScriptinterface {
    Activity mActivity;
    WebView mWebView;
    JsShareClick jsShareClick;

    public JavaScriptinterface(Activity c) {
        mActivity = c;
    }

    public JavaScriptinterface(Activity c, WebView wv) {
        mActivity = c;
        mWebView = wv;
    }

    public JavaScriptinterface(Activity c, JsShareClick jsShareClick) {
        mActivity = c;
        this.jsShareClick = jsShareClick;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void webReload() {
        LogUtils.e("修改值:");
        ((BaseWebActivity) mActivity).reload();
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void setUrl(String url) {
        LogUtils.e("修改值:");
        if (!TextUtils.isEmpty(url)) {
            ((BaseWebActivity) mActivity).url = url;
            ((BaseWebActivity) mActivity).reload();
        }
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void showToast(String content) {
        LogUtils.e("H5打的吐司:" + content);
        ToastUtil.showLong(mActivity, content);
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void showLog(String content) {
        LogUtils.e("H5传过来的信息：" + content);
    }

    /**
     * 关闭当前页面
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void closePage() {
        LogUtils.e("关闭当前页面 closePage");
        //打开选择,本次允许选择的数量
        mActivity.finish();
    }

    /**
     * 随意跳转页面
     *
     * @param clazz
     * @param type
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void jumpActivity(String clazz, String type) {
        try {
            LogUtils.e("执行了 跳转页面的方法  jumpActivity  " + clazz + "    " + type);
            Class<?> aClass = Class.forName(clazz);
            Intent intent1 = new Intent(mActivity, aClass);
            mActivity.startActivityForResult(intent1, 0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随意跳转带id的页面
     *
     * @param clazz
     * @param type
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    public void jumpActivityById(String clazz, String id, String type) {
        try {
            LogUtils.e("执行了 跳转页面的方法  jumpActivityById");
            Class<?> aClass = Class.forName(clazz);
            Intent intent1 = new Intent(mActivity, aClass);
            intent1.putExtra("id", id);
            mActivity.startActivityForResult(intent1, 0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}