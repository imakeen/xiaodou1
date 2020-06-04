package com.radish.framelibrary.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.R;

/**
 * @作者 radish
 * @创建日期 2019/1/14 3:26 PM
 * @邮箱 15703379121@163.com
 * @描述 自定义Banner
 */
public class BannerView extends RelativeLayout {

    // 自定义BannerView - 轮播的Viewpager
    private BannerViewPager mBannerVp;

    // 自定义BannerView - 轮播的指示器
    private RelativeLayout mBannerIndicator;

    // 自定义BannerView - 轮播的标题
    private TextView mBannerTitle;

    // 自定义BannerView - 轮播的指示器的点的容器
    private LinearLayout mBannerPoint;

    // 自定义BannerView - 轮播的ViewPager的适配器
    private IBannerAdapter mAdapter;

    // 自定义属性 - 轮播的指示器的点的宽高
    private float mPointWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private float mPointHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    // 自定义属性 - 轮播的指示器的点的间距
    private float mPointMargin = 20;
    // 自定义属性 - 轮播的指示器的点的选中drawable
    private Drawable mIndicatorFocusDrawable;
    // 自定义属性 - 轮播的指示器的点的默认drawable
    private Drawable mIndicatorNormalDrawable;
    // 自定义属性 - 轮播的指示器背景
    private Drawable mIndicatorDrawable;
    // 自定义属性 - 轮播的标题颜色
    private int mTitleColor;


    // 自定义属性 - 轮播的指示器的布局位置
    private int mCurrentGravityIndicator = RelativeLayout.ALIGN_PARENT_BOTTOM;
    private int mCurrentGravityPoint = RelativeLayout.ALIGN_PARENT_RIGHT;
    private int mCurrentGravityTitle = RelativeLayout.ALIGN_PARENT_LEFT;

    // 自定义BannerView - 轮播的当前位置
    int mCurrentPosition = 0;

    // 上下文
    private Context mContext;
    private String[] mTitles;
    private float mWidthScale = 0;
    private float mHeightScale = 0;

    public BannerView(Context context) {
        this(context, null, 0);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        //把布局加载到这个View里面
        inflate(context, R.layout.layout_banner, this);

        initView();

        initAttribute(attrs);


    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        // 指示器背景图
        mIndicatorDrawable = array.getDrawable(R.styleable.BannerView_indicatorBackground);
        // 指示器点的位置
        mCurrentGravityIndicator = array.getInt(R.styleable.BannerView_indicatorGravity, Gravity.Bottom);

        // 标题颜色
        mTitleColor = array.getColor(R.styleable.BannerView_titleColor, Color.WHITE);
        float titleSize = array.getDimension(R.styleable.BannerView_titleSize, 44);
        mCurrentGravityTitle = array.getInt(R.styleable.BannerView_titleGravity, Gravity.LEFT);

        // 点的位置
        mCurrentGravityPoint = array.getInt(R.styleable.BannerView_pointGravity, Gravity.RIGHT);
        // 默认点样式
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_indicatorPointFocus);
        // 选中点样式
        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_indicatorPointNormal);
        mPointWidth = array.getDimension(R.styleable.BannerView_pointWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPointHeight = array.getDimension(R.styleable.BannerView_pointHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPointMargin = array.getDimension(R.styleable.BannerView_pointMargin, 20);

        //宽高比例
        mWidthScale = array.getFloat(R.styleable.BannerView_widthScale, 0);
        mHeightScale = array.getFloat(R.styleable.BannerView_heightScale, 0);

        //回收
        array.recycle();

        // 未配置设置
        if (mIndicatorFocusDrawable == null)
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        if (mIndicatorNormalDrawable == null)
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        if (mIndicatorDrawable == null)
            mIndicatorDrawable = new ColorDrawable(Color.TRANSPARENT);


        // 设置属性
        setIndicatorBackground(mIndicatorDrawable);
        setGravityByRelativeLayout(mBannerIndicator, mCurrentGravityIndicator, -1);

        setTitleColor(mTitleColor);
        mBannerTitle.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, titleSize);
        setGravityByRelativeLayout(mBannerTitle, mCurrentGravityTitle, -1);

        setGravityByRelativeLayout(mBannerPoint, mCurrentGravityPoint, -1);

    }

    /**
     * 标题文字大小
     *
     * @param size
     * @return
     */
    public BannerView setTitleSize(float size) {
        mBannerTitle.setTextSize(size);
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public BannerView setTitleColor(int color) {
        mTitleColor = color;
        mBannerTitle.setTextColor(mTitleColor);
        return this;
    }

    /**
     * 设置指示器背景
     *
     * @return
     */
    @SuppressLint("NewApi")
    public BannerView setIndicatorBackground(Drawable drawable) {
        mIndicatorDrawable = drawable;
        mBannerIndicator.setBackground(mIndicatorDrawable);
        return this;
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerVp = findViewById(R.id.banner_vp);
        mBannerTitle = findViewById(R.id.banner_tv_title);
        mBannerIndicator = findViewById(R.id.banner_rl_indicator);
        mBannerPoint = findViewById(R.id.banner_ll_indicator);
    }

    public BannerView setPointSize(int width, int height) {
        this.mPointWidth = width;
        this.mPointHeight = height;
        return this;
    }

    public BannerView setPointMargin(int margin) {
        this.mPointMargin = margin;
        return this;
    }

    public BannerView setOnItemClickListener(OnItemClickListener listener) {
        mBannerVp.setOnItemClickListener(listener);
        return this;
    }

    /**
     * 设置ViewPager的条目适配器
     *
     * @param adapter
     */
    public BannerView setAdapter(IBannerAdapter adapter) {
        this.mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        mCurrentPosition = mBannerVp.getCurrentPosition();

        //初始化点的指示器
        initPointIndicator();

        //初始化标题文本
        changeTitle();

        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听当前选中的位置
                pageSelect(position);
            }
        });

        //动态指定高度

        if (mHeightScale > 0 && mWidthScale > 0) {

            post(new Runnable() {
                @Override
                public void run() {
                    int width = getMeasuredWidth();
                    if (width > 0) {
                        int height = (int) (width * mHeightScale / mWidthScale);

                        ViewGroup.LayoutParams params = getLayoutParams();
                        if (params != null && height > 0)
                            getLayoutParams().height = height;
                    }
                }
            });
        }

        return this;
    }

    public BannerView setBannerScale(float widthScale, float heightScale) {
        mWidthScale = widthScale;
        mHeightScale = heightScale;
        return this;
    }

    /**
     * 初始化点的指示器
     */
    private void initPointIndicator() {

        int count = mAdapter.getCount();
        mBannerPoint.removeAllViews();
        if (mIndicatorNormalDrawable instanceof ColorDrawable || mIndicatorFocusDrawable instanceof ColorDrawable) {
            if (mPointWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                mPointWidth = 15;
            }
            if (mPointHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                mPointHeight = 15;
            }
        }
        for (int i = 0; i < count; i++) {
            // 不断地往点的指示器添加内容
            IndicatorPointView indicatorPointView = new IndicatorPointView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mPointWidth, (int) mPointHeight);
            params.leftMargin = (int) mPointMargin;
            indicatorPointView.setLayoutParams(params);
            mBannerPoint.addView(indicatorPointView);
            if (i == mCurrentPosition) {
                //选中位置
                indicatorPointView.setImageDrawable(mIndicatorFocusDrawable);
            } else {
                //未选中位置
                indicatorPointView.setImageDrawable(mIndicatorNormalDrawable);
            }
        }

    }

    /**
     * 页面的切换
     *
     * @param position
     */
    private void pageSelect(int position) {
        // 把之前亮着的点 恢复
        IndicatorPointView prePosition = (IndicatorPointView) mBannerPoint.getChildAt(mCurrentPosition);
        if (prePosition != null) {
            prePosition.setImageDrawable(mIndicatorNormalDrawable);
        }
        // 把当前位置的点  点亮
        mCurrentPosition = mBannerVp.getCurrentPosition();
        IndicatorPointView currentPosition = (IndicatorPointView) mBannerPoint.getChildAt(mCurrentPosition);
        if(currentPosition != null) {
            currentPosition.setImageDrawable(mIndicatorFocusDrawable);
        }
        // 更新Title文本内容
        changeTitle();
    }

    /**
     * 更新Title文本内容
     */
    private void changeTitle() {
        if (mTitles != null && mTitles.length > mCurrentPosition) {
            mBannerTitle.setText(mTitles[mCurrentPosition]);
        }
    }

    /**
     * 设置 指示器点的位置
     *
     * @param gravity
     * @return
     */
    public BannerView setPositionGravity(int gravity) {
        setGravityByRelativeLayout(mBannerPoint, gravity, mCurrentGravityPoint);
        mCurrentGravityPoint = gravity;
        return this;
    }

    /**
     * 设置 标题的位置
     *
     * @param gravity
     * @return
     */
    public BannerView setTitleGravity(int gravity) {
        setGravityByRelativeLayout(mBannerTitle, gravity, mCurrentGravityTitle);
        mCurrentGravityTitle = gravity;
        return this;
    }

    /**
     * 设置 标题的位置
     *
     * @param gravity
     * @return
     */
    public BannerView setIndicatorGravity(int gravity) {
        setGravityByRelativeLayout(mBannerIndicator, gravity, mCurrentGravityIndicator);
        mCurrentGravityIndicator = gravity;
        return this;
    }

    /**
     * 设置 位置Gravity
     *
     * @param view
     * @param addGravity
     * @param removeGravity
     */
    @SuppressLint("NewApi")
    private void setGravityByRelativeLayout(View view, int addGravity, int removeGravity) {
        if (view != null) {
            RelativeLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
            if (removeGravity != -1) {
                params.removeRule(removeGravity);
            }
            if (addGravity != -1)
                params.addRule(addGravity);
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置Banner开始轮询
     */
    public void start() {
        mBannerVp.start();
    }

    /**
     * 设置轮播的起始位置
     *
     * @param startPosition
     */
    public BannerView setStartPosition(int startPosition) {
        mBannerVp.setStartPosition(startPosition);
        return this;
    }

    public BannerView setPointFocusDrawable(Drawable drawable) {
        this.mIndicatorFocusDrawable = drawable;
        return this;
    }

    public BannerView setPointNormalDrawable(Drawable drawable) {
        this.mIndicatorNormalDrawable = drawable;
        return this;
    }

    /**
     * 设置viewPager切换速率
     *
     * @param scrollDuration
     */
    public BannerView setScrollDuration(int scrollDuration) {
        mBannerVp.setScrollDuration(scrollDuration);
        return this;
    }

    public BannerView setTitle(String... titles) {
        this.mTitles = titles;
        return this;
    }

    public class Gravity {
        public static final int LEFT = RelativeLayout.ALIGN_PARENT_LEFT;
        public static final int CENTER = RelativeLayout.CENTER_HORIZONTAL;
        public static final int RIGHT = RelativeLayout.ALIGN_PARENT_RIGHT;
        public static final int TOP = RelativeLayout.ALIGN_PARENT_TOP;
        public static final int Bottom = RelativeLayout.ALIGN_PARENT_BOTTOM;
    }
}
