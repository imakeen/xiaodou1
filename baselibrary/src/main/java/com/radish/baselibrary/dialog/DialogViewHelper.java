package com.radish.baselibrary.dialog;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @作者 radish
 * @创建日期 2018/12/10 4:45 PM
 * @邮箱 15703379121@163.com
 * @描述 dialogView的辅助处理类
 */
class DialogViewHelper {
    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, @LayoutRes int mViewLayoutResId) {
        this(LayoutInflater.from(context).inflate(mViewLayoutResId, null));
    }

    public DialogViewHelper(View view) {
        mContentView = view;
        mViews = new SparseArray<>();
    }

    public void setText(@IdRes int resId, CharSequence text) {
        TextView view = findView(resId);
        if (view != null) view.setText(text);
    }

    public View getView(@IdRes int resId) {
        return findView(resId);
    }

    public String getText(@IdRes int resId) {
        TextView view = findView(resId);
        if (view != null)
            return view.getText().toString().trim();
        else
            return "";
    }

    public <T extends View> T findView(@IdRes int resId) {
        WeakReference<View> viewWeakReference = mViews.get(resId);
        View view = null;
        if (viewWeakReference != null)
            view = viewWeakReference.get();
        if (view == null) {
            view = mContentView.findViewById(resId);
            mViews.put(resId, new WeakReference<View>(view));
        }
        return (T) view;
    }

    public void setClick(final RadishDialog dialog, @IdRes int resId, final OnDialogViewClickListener listener) {
        final View view = findView(resId);
        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(dialog, v);
                    }
                }
            });
    }

    public View getView() {
        return mContentView;
    }


}
