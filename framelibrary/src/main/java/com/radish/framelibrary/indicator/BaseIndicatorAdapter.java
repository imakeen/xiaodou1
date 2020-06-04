package com.radish.framelibrary.indicator;

import android.graphics.Color;

public abstract class BaseIndicatorAdapter implements IIndicatorAdapter {

    private float mOriginTextSize = 14;
    private int mOriginTextColor = Color.parseColor("#333333");
    private float mChangeTextSize = 16;
    private int mChangeTextColor = Color.RED;


    public BaseIndicatorAdapter setOriginTextSize(float textSize) {
        this.mOriginTextSize = textSize;
        return this;
    }

    public BaseIndicatorAdapter setOriginTextColor(int textColor) {
        this.mOriginTextColor = textColor;
        return this;
    }

    public BaseIndicatorAdapter setChangeTextSize(float textSize) {
        this.mChangeTextSize = textSize;
        return this;
    }

    public BaseIndicatorAdapter setChangeTextColor(int textColor) {
        this.mChangeTextColor = textColor;
        return this;
    }


    public float getOriginTextSize() {
        return mOriginTextSize;
    }
    public int getOriginTextColor() {
        return mOriginTextColor;
    }

    public float getChangeTextSize() {
        return mChangeTextSize;
    }

    public int getChangeTextColor() {
        return mChangeTextColor;
    }
}
