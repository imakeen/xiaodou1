package com.radish.baselibrary.navigationbar;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radish.baselibrary.utils.LogUtils;

/**
 * @作者 radish
 * @创建日期 2018/12/13 2:02 PM
 * @邮箱 15703379121@163.com
 * @描述 头部的 builder 基类
 */
public abstract class RadishNavigationBar<P extends RadishNavigationBar.Builder.RadishNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public RadishNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    protected void setText(int resId, String text) {
        if (!TextUtils.isEmpty(text) && resId != 0) {
            TextView view = (TextView) findView(resId);
            if (view != null && view instanceof TextView)
                view.setText(text);
        }
    }

    protected void setText(int resId, String text, int color) {
        if (!TextUtils.isEmpty(text) && resId != 0) {
            TextView view = (TextView) findView(resId);
            if (view != null && view instanceof TextView) {
                view.setText(text);
                view.setTextColor(color);
            }
        }
    }

    protected void setTextColor(int resId, int color) {
        if (resId != 0) {
            TextView view = (TextView) findView(resId);
            if (view != null && view instanceof TextView)
                view.setTextColor(color);
        }
    }

    protected View getView(int resId) {
        if (resId != 0) {
            View view = findView(resId);
            return view;
        }
        return null;
    }

    protected void setImage(int resId, int resIcon) {
        if (resId != 0 && resIcon != 0) {
            View view = findView(resId);
            if (view != null && view instanceof ImageView)
                ((ImageView) view).setImageResource(resIcon);
        }
    }

    protected void setClick(int resId, View.OnClickListener listener) {
        if (listener != null && resId != 0) {
            View view = findView(resId);
            if (view != null)
                view.setOnClickListener(listener);
        }

    }

    protected void setBackground(@IdRes int resId, Drawable drawable) {
        if (resId != 0 && drawable != null) {
            View view = findView(resId);
            if (view != null)
                view.setBackgroundDrawable(drawable);

        }
    }

    private <V extends View> View findView(int resId) {
        return mNavigationView.findViewById(resId);
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        if (mParams.mParent == null && mParams.mContext instanceof Activity) {
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
            if (activityRoot != null && activityRoot.getChildCount() > 0) {
                mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
            }
        }

        if (mParams.mParent != null) {

//        1.创建view
            mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

//        2.添加
            mParams.mParent.addView(mNavigationView, 0);

//        3.
            applyView();
        }
    }

    public abstract static class Builder<B extends Builder> {

        public Builder(Context context, ViewGroup parent) {
            // 创建 P = new...
        }

        public abstract RadishNavigationBar builder();

        protected static class RadishNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            protected RadishNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }
}
