package com.radish.baselibrary.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.radish.baselibrary.R;
import com.radish.baselibrary.base.BaseActivity;
import com.radish.baselibrary.navigationbar.DefaultNavigationBar;
import com.radish.baselibrary.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 带webview 的 Activity 基类
 * 没有重写返回键，有需要的自行重写
 */
public abstract class BaseWebActivity extends BaseActivity {

    public ProgressWebView mWebview;
    public String url;
    public String title;

    private Map<String, String> extraHeaders; // 添加请求头
    private LinearLayout ll;


    @Override
    protected void initTitle() {
        if (!TextUtils.isEmpty(title)) {
            new DefaultNavigationBar.Builder(this,ll).setTitle(title).builder();
        }
    }

    @Override
    protected void initBundle() {
        try {
            Intent intent = getIntent();
            title = intent.getStringExtra("title");// 标题
            url = intent.getStringExtra("url"); // 网址

            String action = intent.getAction();
            if (Intent.ACTION_VIEW.equals(action)) {
                Uri uri = intent.getData();
                if (uri != null) {
                    title = uri.getQueryParameter("title");
                    url = uri.getQueryParameter("url");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.base_activity_web;
    }

    public void reload() {
        LogUtils.e("刷新页面");
        if (mWebview != null)
            mWebview.loadUrl(url);
    }

    /**
     * 处理数据
     */
    private void ToDealWithData() {
        if (TextUtils.isEmpty(title)) {
            title = "逸掌帮";
        }
    }

    @Override
    public void initView() {
        mWebview = findViewById(R.id.wv_base_webview);
        ll = findViewById(R.id.ll);
        if (TextUtils.isEmpty(url)) {
            url = getUrl();
        }
        ToDealWithData();
        initWebView();
    }

    public void initWebView() {
        initGeneralWebView(mWebview);
    }

    public final void initGeneralWebView(ProgressWebView wv) {
        MyWebViewClient myWebViewClient = new MyWebViewClient();
//        wv.setWebChromeClient(new MyWebChromeClient());
        wv.setWebViewClient(myWebViewClient);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        wv.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        wv.getSettings().setSupportZoom(false);//是否可以缩放，默认true
        wv.getSettings().setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        wv.getSettings().setUseWideViewPort(false);//设置此属性，可任意比例缩放。大视图模式
        wv.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//      ******************   缓存设置   *****************
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//
        wv.getSettings().setDomStorageEnabled(false);//DOM Storage
        wv.getSettings().setAppCacheEnabled(false);//是否使用缓存
        wv.getSettings().setDatabaseEnabled(false);   //开启数据库形式存储(false);
//        wv.getSettings().setUserAgentString(MyHttpUtils.getUserAgent(BaseWebActivity.this));//设置用户代理，一般不用
        wv.getSettings().setUserAgentString("yizhangb");//设置用户代理，一般不用
        wv.addJavascriptInterface(new JavaScriptinterface(BaseWebActivity.this, wv), "android");
        wv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        extraHeaders = new HashMap<String, String>();
        extraHeaders.put("device", "android");
        LogUtils.e("WebView: " + url);

        wv.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.destroy();
    }

    public String getUrl() {
        return url;
    }

}