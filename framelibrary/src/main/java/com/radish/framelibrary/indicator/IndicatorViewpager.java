package com.radish.framelibrary.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : radish
 *     e-mail : 15703379121@163.com
 *     time   : 2019/4/7
 *     desc   : 解决viewPager与Scrollview的冲突
 * </pre>
 */

/**
 * Created by  HONGDA on 2018/6/15.
 */
public class IndicatorViewpager extends ViewPager {

    //是否可以进行滑动
    private boolean canScroll = false;//默认可以滑动

    public IndicatorViewpager(Context context) {
        super(context);
    }

    public IndicatorViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return canScroll;
//    }
}
