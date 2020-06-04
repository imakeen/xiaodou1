package com.radish.baselibrary.dialog;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @作者 radish
 * @创建日期 2018/12/10 4:45 PM
 * @邮箱 15703379121@163.com
 * @描述 dialog控制器
 */
public class DialogController {
    private RadishDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public RadishDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public DialogController(RadishDialog radishDialog, Window window) {
        this.mDialog = radishDialog;
        this.mWindow = window;
    }

    public void setText(@IdRes int resId, CharSequence text) {
        if (mViewHelper != null)
            mViewHelper.setText(resId, text);
    }

    public View getView(@IdRes int resId) {
        if (mViewHelper != null)
            return mViewHelper.getView(resId);
        return null;
    }

    public String getText(@IdRes int resId) {
        if (mViewHelper != null)
            return mViewHelper.getText(resId);
        return "";
    }

    public <T extends View> T findView(@IdRes int resId) {
        if (mViewHelper != null)
            return mViewHelper.findView(resId);
        return null;
    }

    public void setOnClickListener(RadishDialog dialog, @IdRes int resId, OnDialogViewClickListener listener) {
        if (mViewHelper != null)
            mViewHelper.setClick(dialog, resId, listener);
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    public static class DialogParams {
        public Context mContext;
        // dialog 消失监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog dismiss 监听
        public DialogInterface.OnDismissListener mOnDismissListener;

        public @StyleRes
        int mThemeResId;
        //布局View
        public View mView;
        //布局layoutId
        public @LayoutRes
        int mViewLayoutResId;
        // 按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局样式
        public int mWidth = ActionBar.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimations = 0;
        //位置
        public int mGravity = Gravity.CENTER;
        //高度
        public int mHeight = ActionBar.LayoutParams.WRAP_CONTENT;
        //焦点
        public int mFocus = 0;
        //点击空白处时是否取消
        public boolean touchOutSideCancel = true;
        //点击返回按钮处时是否取消
        public boolean clickBackCancel = true;

        // 存放View文本
        SparseArray<CharSequence> mTextArray = new SparseArray<>();
        // 存放ClickListener
        SparseArray<OnDialogViewClickListener> mClickArray = new SparseArray<>();

        public DialogParams(Context context, @StyleRes int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定参数
         */
        public void apply(DialogController controller) {
            // 设置布局
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) viewHelper = new DialogViewHelper(mView);

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setView()");
            }

            //给dialog设置布局
            controller.getDialog().setContentView(viewHelper.getView());

            controller.getDialog().setCanceledOnTouchOutside(touchOutSideCancel);

            controller.getDialog().setCancelable(clickBackCancel);

            // 设置文本
            for (int i = 0; i < mTextArray.size(); i++)
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));

            // 设置点击
            for (int i = 0; i < mClickArray.size(); i++) {
                viewHelper.setClick(controller.getDialog(), mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //设置controller的viewHelper
            controller.setViewHelper(viewHelper);

            // 配置
            Window window = controller.getWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }


            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }
}
