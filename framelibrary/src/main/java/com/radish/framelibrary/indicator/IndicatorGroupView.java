package com.radish.framelibrary.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2019/1/19 2:04 PM
 * @邮箱 15703379121@163.com
 * @描述 Indicator的容器 包含item及底部指示器
 */
public class IndicatorGroupView extends FrameLayout {

    private LinearLayout mIndicatorGroup;
    private View mBottomTrackView;
    private LayoutParams mBottomTrackParams;
    private List<Integer> mItemWidths;
    private int mBottomTrackWidth = -1;

    public IndicatorGroupView(Context context) {
        this(context, null);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    /**
     * 添加itemView
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    public View getItemAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    public int getItemCount() {
        return mIndicatorGroup.getChildCount();
    }

    public void removeAllItems() {
        mIndicatorGroup.removeAllViews();
    }

    public ViewGroup getIndicatorGroup() {
        return mIndicatorGroup;
    }

    /**
     * 添加底部指示器
     *
     * @param bottomTrackView 底部指示器
     * @param position        当前条目位置
     * @param itemWidths      各条目宽度
     */
    public void addBottomTrackView(View bottomTrackView, int position, List<Integer> itemWidths) {
        if (bottomTrackView == null) {
            return;
        }

        if (itemWidths != null) {
            mItemWidths = itemWidths;
        }

        if (mBottomTrackView != null)
            return;
        this.mBottomTrackView = bottomTrackView;

        addView(mBottomTrackView);

        mBottomTrackParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mBottomTrackParams.gravity = Gravity.BOTTOM;
        scrollBottomTrack(position, 0);
    }

    public void setBottomTrackViewWidth(int width) {
        mBottomTrackWidth = width;
    }

    /**
     * 滚动底部指示器  ->  leftMargin
     * 15232370276
     *
     * @param position
     * @param positionOffset
     */
    public void scrollBottomTrack(int position, float positionOffset) {
        if (mItemWidths == null || mItemWidths.size() == 0) {
            return;
        }
        float totalScroll = 0;
        for (int i = 0; i < position; i++) {
            totalScroll += mItemWidths.get(i);
        }
        if (position < mItemWidths.size()) {
            int currentItemWidth = mItemWidths.get(position);
            totalScroll += currentItemWidth * positionOffset;
        }
        int itemWidth = mItemWidths.get(position);
        if (mBottomTrackWidth != -1) {
            mBottomTrackParams.width = mBottomTrackWidth;
            mBottomTrackParams.leftMargin = (int) totalScroll + ((itemWidth - mBottomTrackWidth) / 2);
        } else {
            mBottomTrackParams.leftMargin = (int) totalScroll;
        }
        mBottomTrackView.setLayoutParams(mBottomTrackParams);
    }
}
