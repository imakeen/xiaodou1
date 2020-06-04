package com.radish.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.skin.SkinManager;
import com.radish.framelibrary.skin.SkinResource;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:46 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResouce();
            ColorStateList color = skinResource.getColorByName(resName);

            if (!(view instanceof TextView)) {
                LogUtils.e("This " + view + " can't to change TextView");
                return;
            }
            if (color != null) {
                TextView textView = (TextView) view;
                textView.setTextColor(color);
            }
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResouce();
            Drawable drawable = skinResource.getDrawableByName(resName);

            if (drawable == null) {
                drawable = skinResource.getMipmapByName(resName);
            }
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
                return;
            }
            ColorStateList color = skinResource.getColorByName(resName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            if (!(view instanceof ImageView)) {
                LogUtils.e("This " + view + " can't to change ImageView");
                return;
            }
            SkinResource skinResource = getSkinResouce();
            Drawable drawable = skinResource.getDrawableByName(resName);

            if (drawable == null) {
                drawable = skinResource.getMipmapByName(resName);
            }
            if (drawable != null) {
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    };

    protected SkinResource getSkinResouce() {
        return SkinManager.getInstance().getSkinResource();
    }

    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }
}
