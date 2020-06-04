package com.radish.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.radish.baselibrary.R;

/**
 * @作者 radish
 * @创建日期 2019/2/28 3:00 PM
 * @邮箱 15703379121@163.com
 * @描述 默认显示一个居中自适应的popup
 */
public class PopupUtil {

    private PopupWindow mPopupWindow;
    private boolean mTouchable = true;
    private int mGravity = Gravity.CENTER;
    private View mPopupView;
    private View mBasisView;
    private Drawable mBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
    private int mLocationX = 0;
    private int mLocationY = 0;
    private int mPopupWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    private int mPopupHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private boolean mIsRefresh = false;
    private int mAnimation = R.style.dialog_scale_anim;
    private PopupWindow.OnDismissListener mDismissListener;
    private float mBgAlpha = 1;

    public PopupUtil setAlpha(float bgAlpha) {
        this.mBgAlpha = bgAlpha;
        return this;
    }

    public static class Direction {
        public static int ABOVE = 0x0011;
        public static int UNDER = 0x0022;
    }

    private static PopupUtil mPopup;
    private Context mContext;

    private PopupUtil(Context context, View popupView) {
        this.mContext = context;
        this.mPopupView = popupView;
    }

    public static PopupUtil getInstance(Context context, @LayoutRes int layoutRes) {
        return getInstance(context, LayoutInflater.from(context).inflate(layoutRes, null));
    }

    public static PopupUtil getInstance(Context context, View popupView) {
        if (mPopup == null) {
            synchronized (PopupUtil.class) {
                if (mPopup == null) {
                    mPopup = new PopupUtil(context, popupView);
                }
            }
        }
        return mPopup;
    }

    /**
     * 显示popup
     *
     * @return
     */
    public PopupUtil show() {
        setBackgroundAlpha(mBgAlpha);//设置屏幕透明度
        if (!mIsRefresh && mPopupWindow != null) {
            if (!mPopupWindow.isShowing())
                mPopupWindow.showAtLocation(mBasisView, mGravity, mLocationX, mLocationY);
            return this;
        }
        showRefresh();
        return this;
    }

    /**
     * 隐藏popup
     *
     * @return
     */
    public PopupUtil dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        return this;
    }

    /**
     * 从底部显示popup
     *
     * @return
     */
    public PopupUtil showBottom() {
        mAnimation = R.style.dialog_from_bottom_anim;
        mPopupWidth = WindowManager.LayoutParams.MATCH_PARENT;
        mGravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        show();
        return this;
    }

    /**
     * 在某个空间上方/下方显示popup
     *
     * @param direction
     * @return
     */
    public PopupUtil showDirection(int direction) {
        int location[] = new int[2];
        int x, y;
        mBasisView.getLocationOnScreen(location);
        x = location[0];
        y = location[1];
        mAnimation = R.style.popup_under_anim;

        mPopupWidth = mBasisView.getWidth();
        mGravity = Gravity.NO_GRAVITY;
        mLocationX = x + DensityUtil.dp2px(mContext, 1f);
        if (direction == Direction.UNDER) {
            int h = mBasisView.getHeight();
            mLocationY = y + h;
        } else {
            int h = mPopupView.getHeight();
            mLocationY = y - h;
        }
        show();
        return this;
    }

    public PopupUtil setDismissListener(PopupWindow.OnDismissListener listener) {
        this.mDismissListener = listener;
        return this;
    }

    public PopupUtil setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public PopupUtil setPopupView(View popupView) {
        this.mPopupView = popupView;
        return this;
    }

    public PopupUtil setPopupView(@LayoutRes int layoutRes) {
        mPopupView = LayoutInflater.from(mContext).inflate(layoutRes, null);
        return this;
    }

    public PopupUtil setBackgroundDrawable(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        return this;
    }

    public PopupUtil setLocation(int x, int y) {
        this.mLocationX = x;
        this.mLocationY = y;
        return this;
    }

    public PopupUtil setBasisView(View basisView) {
        this.mBasisView = basisView;
        return this;
    }

    public PopupUtil setPopupSize(int width, int height) {
        this.mPopupWidth = width;
        this.mPopupHeight = height;
        return this;
    }

    public PopupUtil setRefresh(boolean isRefresh) {
        this.mIsRefresh = isRefresh;
        return this;
    }

    public PopupUtil setOutsideTouchable(boolean touchable) {
        this.mTouchable = touchable;
        return this;
    }

    public View getPopupView() {
        return mPopupView;
    }

    public PopupUtil showRefresh() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        if (mBasisView == null)
            mBasisView = mPopupView.getRootView();
        mPopupWindow = new PopupWindow(mPopupView, mPopupWidth,
                mPopupHeight, false) {
            /**
             * 主动约束PopuWindow的内容大小，重写showAsDropDown方法
             * 解决android7.0上布局不约束问题
             *
             * @param anchor
             */
            @Override
            public void showAsDropDown(View anchor) {
                if (Build.VERSION.SDK_INT >= 24) {
                    Rect visibleFrame = new Rect();
                    anchor.getGlobalVisibleRect(visibleFrame);
                    int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                    setHeight(height);
                }
                super.showAsDropDown(anchor);
            }
        };


        mPopupWindow.setAnimationStyle(mAnimation);
//        // 允许点击外部消失
        mPopupWindow.setBackgroundDrawable(mBackgroundDrawable);
        mPopupWindow.setOutsideTouchable(mTouchable);   //设置外部点击关闭ppw窗口
        mPopupWindow.showAtLocation(mBasisView, mGravity, mLocationX, mLocationY);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
                if (mDismissListener != null){
                    mDismissListener.onDismiss();
                }
            }
        });

        return this;
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        if (mContext instanceof Activity) {
            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                    .getAttributes();
            lp.alpha = bgAlpha;
            ((Activity) mContext).getWindow().setAttributes(lp);
        }
    }

    public void release() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        mContext = null;
        mPopupWindow = null;
        mPopupView = null;
        mPopup = null;
    }
}
