package com.radish.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * @作者 radish
 * @创建日期 2018/11/17 3:16 PM
 * @邮箱 15703379121@163.com
 * @描述 View的findViewById的辅助类
 */
public class ViewFinder {
    private View mView;
    private Activity mActivity;

    public ViewFinder(View view) {
        this.mView = view;
    }

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(int viewId){
        return mActivity != null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
