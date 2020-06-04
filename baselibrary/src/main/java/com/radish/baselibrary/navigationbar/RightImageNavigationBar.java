package com.radish.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.radish.baselibrary.R;


/**
 * @作者 radish
 * @创建日期 2018/12/13 2:31 PM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class RightImageNavigationBar extends RadishNavigationBar<RightImageNavigationBar.Builder.RightImageNavigationParams> {
    private final Builder.RightImageNavigationParams mParams;

    public RightImageNavigationBar(RightImageNavigationBar.Builder.RightImageNavigationParams params) {
        super(params);
        this.mParams = params;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar_right_image;
    }


    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getParams().mTitle);
        setImage(R.id.left, getParams().mLeftIcon);
        setImage(R.id.right, getParams().mRightIcon);
        setClick(R.id.right, getParams().mRightListener);
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
        RightImageNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new RightImageNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new RightImageNavigationParams(context, null);
        }

        @Override
        public RightImageNavigationBar builder() {
            RightImageNavigationBar navigationBar = new RightImageNavigationBar(P);
            return navigationBar;
        }

        //              1.设置所有效果
        public RightImageNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public RightImageNavigationBar.Builder setLeftIcon(int leftIcon) {
            P.mLeftIcon = leftIcon;
            return this;
        }

        public RightImageNavigationBar.Builder setRightIcon(int rightIcon) {
            P.mRightIcon = rightIcon;
            return this;
        }

        public RightImageNavigationBar.Builder setRightClick(View.OnClickListener rightListener) {
            P.mRightListener = rightListener;
            return this;
        }

        public RightImageNavigationBar.Builder setLefClick(View.OnClickListener leftListener) {
            P.mLeftListener = leftListener;
            return this;
        }

        public RightImageNavigationBar.Builder setBackground(Drawable drawable) {
            P.mBackgroundDrawable = drawable;
            return this;
        }

        public RightImageNavigationBar.Builder setLineDrawable(Drawable drawable) {
            P.mLineDrawable = drawable;
            return this;
        }

        public RightImageNavigationBar.Builder setLineColor(int color) {
            setLineDrawable(new ColorDrawable(color));
            return this;
        }


        protected static class RightImageNavigationParams extends RadishNavigationParams {
            //            2.所有效果的放置
            public String mTitle = "";
            public int mLeftIcon = R.drawable.ic_back;
            public int mRightIcon = 0;
            public View.OnClickListener mRightListener;
            public View.OnClickListener mLeftListener;
            public Drawable mBackgroundDrawable;
            public Drawable mLineDrawable;

            RightImageNavigationParams(Context context, ViewGroup parent) {
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

    public void setRightIcon(int rightIcon) {
        setImage(R.id.right, rightIcon);
    }

    public void setRightClick(View.OnClickListener rightListener) {
        setClick(R.id.right, rightListener);
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
