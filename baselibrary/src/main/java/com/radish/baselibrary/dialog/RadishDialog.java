package com.radish.baselibrary.dialog;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.radish.baselibrary.R;
import com.radish.baselibrary.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * @作者 radish
 * @创建日期 2018/12/10 4:43 PM
 * @邮箱 15703379121@163.com
 * @描述 万能dialog
 */
public class RadishDialog extends Dialog {
    private DialogController mController;

    public RadishDialog(Context context) {
        super(context);
    }

    public RadishDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mController = new DialogController(this, getWindow());
    }

    protected RadishDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private final DialogController.DialogParams P;

        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#theme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(Context context) {
            this(context, R.style.radishDialog);
        }

        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         */
        public Builder(Context context, @StyleRes int themeResId) {
            P = new DialogController.DialogParams(context, themeResId);
        }

        /**
         * Creates an {@link RadishDialog} with the arguments supplied to this
         * builder.
         */
        public RadishDialog create() {

            // Context has already been wrapped with the appropriate theme.
            final RadishDialog dialog = new RadishDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mController);
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            if (P.mFocus != 0) {
                LogUtils.e("aaaa");
                View view = dialog.findView(P.mFocus);
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
            }
            return dialog;
        }

        /**
         * Creates an {@link RadishDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         */
        public RadishDialog show() {
            final RadishDialog dialog = create();
            dialog.show();
            return dialog;
        }


        /**
         * Sets a custom view to be the contents of the alert dialog.
         */
        public RadishDialog.Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public RadishDialog.Builder setView(@LayoutRes int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        public RadishDialog.Builder setCanceledOnTouchOutside(boolean isCanceled) {
            P.touchOutSideCancel = isCanceled;
            return this;
        }

        public RadishDialog.Builder setCanceledOnClickBack(boolean isCanceled) {
            P.clickBackCancel = isCanceled;
            return this;
        }

        public RadishDialog.Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        public RadishDialog.Builder setFocus(int viewId) {
            P.mFocus = viewId;
            return this;
        }


        public RadishDialog.Builder setClick(int viewId, OnDialogViewClickListener listener) {
            P.mClickArray.put(viewId, listener);
            return this;
        }
        /**
         * Sets the callback that will be called if the dialog is canceled.
         */
        public RadishDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         */
        public RadishDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         */
        public RadishDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 全屏
         */
        public RadishDialog.Builder setFullWidth() {
            P.mWidth = ActionBar.LayoutParams.MATCH_PARENT;
            return this;
        }


        /**
         * 全屏
         */
        public RadishDialog.Builder setWidth(int width) {
            P.mWidth = width;
            return this;
        }

        /**
         * 底部弹出
         */
        public Builder setFromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimations = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }


        /**
         * 设置宽高
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }


        /**
         * 添加默认动画
         */
        public Builder addDefaultAnimation() {
            P.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 添加动画
         *
         * @param styleAnimation
         * @return
         */
        public Builder setAnimation(int styleAnimation) {
            P.mAnimations = styleAnimation;
            return this;
        }

    }

    public void setText(@IdRes int resId, CharSequence text) {
        mController.setText(resId, text);
    }

    public View getView(@IdRes int resId) {
        return mController.getView(resId);
    }

    public String getText(@IdRes int resId) {
        return mController.getText(resId);
    }

    public <T extends View> T findView(@IdRes int resId) {
        return mController.findView(resId);
    }

    public void setClick(@IdRes int resId, OnDialogViewClickListener listener) {
        mController.setOnClickListener(this, resId, listener);
    }

    @Override
    public void show() {
        super.show();

        final View view = getCurrentFocus();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    if (view instanceof TextView) {
                        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    }
                }
            });
        }
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }
}
