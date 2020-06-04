package com.radish.framelibrary.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @作者 radish
 * @创建日期 2019/1/11 9:37 AM
 * @邮箱 15703379121@163.com
 * @描述 改变BannerViewPager切换速率
 */
public class BannerScroller extends Scroller {

    //BannerViewPager切换动画持续的时间
    private int mScrollDuration = 1500;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

}
