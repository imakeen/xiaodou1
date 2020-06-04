package com.radish.framelibrary.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.radish.framelibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2019/1/17 2:12 PM
 * @邮箱 15703379121@163.com
 * @描述 ViewPager指示器
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    // 指示器的adapter
    private BaseIndicatorAdapter mAdapter;

    // 指示器条目数
    private int mCount = 0;

    // 动态添加View - 指示器条目的容器
    private IndicatorGroupView mIndicatorGroup;

    // 当前可见的屏幕显示几个条目  -  默认0，自适应
    private int mVisibleNum = 0;

    // 当总条目宽度超过屏幕宽度时，条目宽度样式  -  默认 Style.WIDTH_SELF_ADAPTER 自适应宽度，条目间间隔一致。
    private int mItemStyle = Style.WIDTH_SELF_ADAPTER;

    // 条目宽度
    private List<Integer> mItemWidths = new ArrayList<>();

    private int mCurrentPosition = 0;

    // viewPager
    private ViewPager mViewPager;
    private Integer mItemPadding = 80;
    private int mOriginTextColor;
    private int mChangeTextColor;
    private float mOriginTextSize;
    private float mChangeTextSize;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化指示器条目的容器

        mIndicatorGroup = new IndicatorGroupView(context);
        addView(mIndicatorGroup);

        initAttribute(context, attrs);
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttribute(Context context, AttributeSet attrs) {
        // 指定item的宽度
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);

        //当前屏幕可见条目数量
        mVisibleNum = array.getInteger(R.styleable.TrackIndicatorView_TabVisibleNum, 0);


        mOriginTextColor = array.getColor(R.styleable.TrackIndicatorView_originColor, getResources().getColor(R.color.gray6));
        mChangeTextColor = array.getColor(R.styleable.TrackIndicatorView_changeColor, Color.RED);
        float originTextSize = array.getDimension(R.styleable.TrackIndicatorView_originSize, 46);
        float changeTextSize = array.getDimension(R.styleable.TrackIndicatorView_changeSize, 50);

        array.recycle();
        mOriginTextSize = originTextSize / getResources().getDisplayMetrics().scaledDensity;
        mChangeTextSize = changeTextSize / getResources().getDisplayMetrics().scaledDensity;

    }

    public void setAdapter(BaseIndicatorAdapter adapter) {
        this.setAdapter(null, adapter);
    }

    private void switchItemClick(final View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });
    }

    public ViewGroup getIndicatorGroup() {
        return mIndicatorGroup.getIndicatorGroup();
    }

    public void setAdapter(ViewPager viewPager, BaseIndicatorAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("IIndicatorAdapter is null");
        }

        this.mAdapter = adapter;

        //初始化样式
        mAdapter.setOriginTextColor(mOriginTextColor);
        mAdapter.setChangeTextColor(mChangeTextColor);

        mAdapter.setOriginTextSize(mOriginTextSize);
        mAdapter.setChangeTextSize(mChangeTextSize);


        if (viewPager != null) {
            this.mViewPager = viewPager;
            viewPager.addOnPageChangeListener(this);
        }

        this.mCount = mAdapter.getCount();
        mIndicatorGroup.removeAllItems();
        for (int i = 0; i < mCount; i++) {
            View itemView = mAdapter.getView(i, mIndicatorGroup.getIndicatorGroup());
            mIndicatorGroup.addItemView(itemView);

            if (mViewPager != null) {
                // 设置点击事件
                switchItemClick(itemView, i);
            }
        }
//        initItemWidth();


        // 初始化数据
        if (mAdapter != null)
            mAdapter.highLightIndicator(mIndicatorGroup.getItemAt(mCurrentPosition), mCurrentPosition);
        if (mViewPager != null)
            mViewPager.setCurrentItem(mCurrentPosition);

        if (getMeasuredWidth() != 0) {
            initItemWidth();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            initItemWidth();
        }
    }

    public TrackIndicatorView setVisibleNum(int mVisibleNum) {
        this.mVisibleNum = mVisibleNum;
        return this;
    }

    private void initItemWidth() {
        getItemWidth();
        for (int i = 0; i < mCount; i++) {
            if (mItemWidths.get(i) != 0) {
                mIndicatorGroup.getItemAt(i).setMinimumWidth(mItemWidths.get(i));
            }
        }
        // 添加底部跟踪的指示器
        if (mItemWidths.size() > 0) {
            mIndicatorGroup.addBottomTrackView(mAdapter.getBottomTrackView(), mCurrentPosition, mItemWidths);
        }
    }

    public void setBottomTrackWidth(int width) {
        mIndicatorGroup.setBottomTrackViewWidth(width);
    }

    /**
     * 获取item的宽度
     *
     * @return
     */
    private void getItemWidth() {
        int parentWidth = getWidth();
        if (mVisibleNum != 0) {
            mItemWidths.clear();
            for (int i = 0; i < mCount; i++) {
                mItemWidths.add(parentWidth / mVisibleNum);
            }
        } else {
            // 没有指定
            // 获取最宽的
            if (mItemStyle == Style.WIDTH_AVERAGE) {
                int maxItemWidth = 0;
                int itemWidth = 0;
                for (int i = 0; i < mCount; i++) {
                    int currentItemWidth = mIndicatorGroup.getItemAt(i).getWidth();
                    maxItemWidth = Math.max(currentItemWidth, maxItemWidth);
                }
                itemWidth = maxItemWidth;
                if (maxItemWidth * mCount < parentWidth) {
                    itemWidth = parentWidth / mCount;
                }
                mItemWidths.clear();
                for (int i = 0; i < mIndicatorGroup.getItemCount(); i++) {
                    mItemWidths.add(itemWidth);
                }
            } else if (mItemStyle == Style.WIDTH_SELF_ADAPTER) {
                int totalItemWidth = 0;
                mItemWidths.clear();
                for (int i = 0; i < mCount; i++) {
                    Integer itemWidth = mIndicatorGroup.getItemAt(i).getWidth() + mItemPadding;
                    mItemWidths.add(itemWidth);
                    totalItemWidth += itemWidth;
                }
                if (totalItemWidth < parentWidth) {
                    float itemAddWidth = (parentWidth * 1f - totalItemWidth) / mCount;
                    mItemWidths.clear();
                    mItemWidths.clear();
                    for (int i = 0; i < mCount; i++) {
                        int itemWidth = mIndicatorGroup.getItemAt(i).getWidth();
                        mItemWidths.add(i, (int) (itemWidth + itemAddWidth));
                    }
                }
            }
        }
    }

    public View getItemView(int position) {
        return mIndicatorGroup.getItemAt(position);
    }

    public int getItemCount() {
        return mCount;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滚动的时候不断地调用
        scrollCurrentIndicator(position, positionOffset);

        //底部指示器的变化
        mIndicatorGroup.scrollBottomTrack(position, positionOffset);

    }

    /**
     * 滚动的时候不断地调用【文字的变化】
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        float totalScroll = 0;
        int offsetScroll = 0;
        for (int i = 0; i < position; i++) {
            totalScroll += mItemWidths.get(i);
        }
        if (position < mItemWidths.size()) {
            int currentItemWidth = mItemWidths.get(position);
            totalScroll += currentItemWidth * positionOffset;
            offsetScroll = (getWidth() - currentItemWidth) / 2;
        }
        final int finalScroll = (int) (totalScroll - offsetScroll);
        scrollTo(finalScroll, 0);
    }

    @Override
    public void onPageSelected(int position) {
        // 将当前位置点亮，上一个位置重置
        mAdapter.restoreIndicator(mIndicatorGroup.getItemAt(mCurrentPosition), mCurrentPosition);
        mCurrentPosition = position;
        mAdapter.highLightIndicator(mIndicatorGroup.getItemAt(mCurrentPosition), mCurrentPosition);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public TrackIndicatorView setOriginTextColor(int originTextColor) {
        this.mOriginTextColor = originTextColor;
        return this;
    }

    public TrackIndicatorView setChangeTextColor(int changeTextColor) {
        this.mChangeTextColor = changeTextColor;
        return this;
    }

    public TrackIndicatorView setOriginTextSize(float originTextSize) {
        this.mOriginTextSize = originTextSize;
        return this;
    }

    public TrackIndicatorView setChangeTextSize(float changeTextSize) {
        this.mChangeTextSize = changeTextSize;
        return this;
    }


    /**
     * 显示样式
     */
    public class Style {
        // 条目统一宽度，条目宽度一致
        public static final int WIDTH_AVERAGE = 0x0011;
        // 自适应宽度，条目间间隔一致
        public static final int WIDTH_SELF_ADAPTER = 0x0022;
    }
}
