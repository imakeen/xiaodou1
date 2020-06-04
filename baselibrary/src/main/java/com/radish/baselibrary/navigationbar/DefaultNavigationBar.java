package com.radish.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.radish.baselibrary.R;


/**
 * @作者 radish
 * @创建日期 2018/12/13 2:31 PM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class DefaultNavigationBar extends RadishNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {
    private final Builder.DefaultNavigationParams mParams;

    public DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
        this.mParams = params;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }


    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getParams().mTitle);
        setImage(R.id.left, getParams().mLeftIcon);
        setText(R.id.right, getParams().mRightTitle, getParams().mRightTitleColor);
        setClick(R.id.right, getParams().mRightListener);
        setImage(R.id.iv_right, getParams().mRightIcon);
        setClick(R.id.iv_right, getParams().mIvRightListener);
        setBackground(R.id.rl, getParams().mBackgroundDrawable);
        setBackground(R.id.view_line, getParams().mLineDrawable);

        if (getParams().mLeftListener == null) {
            setClick(R.id.left, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getParams().mContext instanceof Activity) {
                        ((Activity) getParams().mContext).finish();
                    }
                }
            });
        } else {
            setClick(R.id.left, getParams().mLeftListener);
        }


    }

    public static class Builder extends RadishNavigationBar.Builder {
        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        //              1.设置所有效果
        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public Builder setLeftIcon(int leftIcon) {
            P.mLeftIcon = leftIcon;
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            P.mRightIcon = rightIcon;
            return this;
        }

        public Builder setRightTitle(String rightTitle) {
            P.mRightTitle = rightTitle;
            return this;
        }

        public Builder setRightTitle(String rightTitle, int color) {
            P.mRightTitle = rightTitle;
            P.mRightTitleColor = color;
            return this;
        }

        public Builder setRightClick(View.OnClickListener rightListener) {
            P.mRightListener = rightListener;
            return this;
        }


        public Builder setIvRightClick(View.OnClickListener rightListener) {
            P.mIvRightListener = rightListener;
            return this;
        }

        public Builder setLefClick(View.OnClickListener leftListener) {
            P.mLeftListener = leftListener;
            return this;
        }

        public Builder setBackground(Drawable drawable) {
            P.mBackgroundDrawable = drawable;
            return this;
        }

        public Builder setLineDrawable(Drawable drawable) {
            P.mLineDrawable = drawable;
            return this;
        }

        public Builder setLineColor(int color) {
            setLineDrawable(new ColorDrawable(color));
            return this;
        }

        protected static class DefaultNavigationParams extends RadishNavigationBar.Builder.RadishNavigationParams {
            //            2.所有效果的放置
            public String mTitle = "";
            public int mLeftIcon = R.drawable.ic_back;
            public int mRightIcon;
            public String mRightTitle = "";
            public int mRightTitleColor = Color.GRAY;
            public View.OnClickListener mRightListener;
            public View.OnClickListener mIvRightListener;
            public View.OnClickListener mLeftListener;
            public Drawable mBackgroundDrawable;
            public Drawable mLineDrawable;

            DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }

    //              1.设置所有效果
    public void setTitle(String title) {
        setText(R.id.title, title);
    }

    public void setLeftIcon(int leftIcon) {
        setImage(R.id.left, leftIcon);
    }

    public View getRightIcon() {
        return getView(R.id.iv_right);
    }

    public View getRightTitle() {
        return getView(R.id.right);
    }

    public View getLeftIcon() {
        return getView(R.id.left);
    }

    public void setRightIcon(int rightIcon) {
        setImage(R.id.iv_right, rightIcon);
    }

    public void setRightTitle(String rightTitle) {
        setText(R.id.right, rightTitle);
    }

    public void setRightTitle(String rightTitle, int color) {
        setText(R.id.right, rightTitle, color);
    }

    public void setRightClick(View.OnClickListener rightListener) {
        setClick(R.id.right, rightListener);
    }

    public void setIvRightClick(View.OnClickListener rightListener) {
        setClick(R.id.iv_right, rightListener);
    }

    public void setLefClick(View.OnClickListener leftListener) {
        setClick(R.id.left, leftListener);
    }

    public void setBackground(Drawable drawable) {
        setBackground(R.id.rl, drawable);
    }

    public void setLineDrawable(Drawable drawable) {
        setBackground(R.id.view_line, drawable);
    }

    public void setLineColor(int color) {
        setLineDrawable(new ColorDrawable(color));
    }

}
