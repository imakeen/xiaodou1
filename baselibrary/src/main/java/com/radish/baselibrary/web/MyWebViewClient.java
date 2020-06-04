package com.radish.baselibrary.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.radish.baselibrary.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Create_time: 2018/4/25 on 17:15.
 * @ description：
 * @ author: vchao  blog: http://blog.csdn.net/zheng_weichao
 */
public class MyWebViewClient extends WebViewClient {
    //重写父类方法，让新打开的网页在当前的WebView中显示
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        HashMap extraHeaders = new HashMap<String, String>();
        extraHeaders.put("device", "android");
        view.loadUrl(url, extraHeaders);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtils.e("urll===============  " + url);

    }

    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      final WebResourceRequest request) {
        if (request != null && request.getUrl() != null) {
            String scheme = request.getUrl().getScheme().trim();
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                return super.shouldInterceptRequest(view, new WebResourceRequest() {
                    @Override
                    public Uri getUrl() {
                        return request.getUrl();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public boolean isForMainFrame() {
                        return request.isForMainFrame();
                    }

                    @Override
                    public boolean isRedirect() {
                        return false;
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public boolean hasGesture() {
                        return request.hasGesture();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public String getMethod() {
                        return request.getMethod();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public Map<String, String> getRequestHeaders() {
                        request.getRequestHeaders().put("device", "android");
                            /*HashMap extraHeaders = new HashMap<String, String>();
            extraHeaders.put();
            extraHeaders.put();
            LogUtils.e("WebView:  " + url);
            view.loadUrl(url, extraHeaders);*/
                        return request.getRequestHeaders();
                    }
                });
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (!TextUtils.isEmpty(url) && Uri.parse(url).getScheme() != null) {
            String scheme = Uri.parse(url).getScheme().trim();
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {

                return super.shouldInterceptRequest(view, url);
            }
        }
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler
            handler, SslError
                                           error) {
        LogUtils.e("SSL授权失败");
        //handler.cancel(); 默认的处理方式，WebView变成空白页
//                        //接受证书
        handler.proceed();
        super.onReceivedSslError(view, handler, error);
        //handleMessage(Message msg); 其他处理
    }
}