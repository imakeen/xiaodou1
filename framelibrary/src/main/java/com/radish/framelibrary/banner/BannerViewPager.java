package com.radish.framelibrary.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.radish.framelibrary.RadishActivityLifecycleCallbacks;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;


/**
 * @作者 radish
 * @创建日期 2019/1/10 11:47 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class BannerViewPager extends ViewPager {

    // 实现自动轮询 - 发送给消息的msgWhat
    private static final int SCROLL_MSG = 0x0011;

    // 这是BannerViewPager的自定义的adapter
    private IBannerAdapter mAdapter;

    // 实现自动轮询 - 发送消息handler
    private Handler mHandler = new MyHandler(this);

    // 实现自动轮询 - 页面切换时间间隔
    private long mCutDownTime = 5000;

    private int startPosition = Integer.MAX_VALUE / 2;

    // 改变ViewPager切换的速率- 自定义的页面切换的Scroller
    private BannerScroller mBannerScroller;

    // viewPager条目复用
    private Set<View> mConvertViews;

    // viewPager的条目数
    private int mCount;

    // 是否开启循环
    private Boolean mIsStart = false;
    private OnItemClickListener mItemClickListener;
    private BannerPagerAdapter mPagerAdapter;

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //改变ViewPager切换的速率
        //改变duration持续时间，private，无法改变
        //改变mScroller, private ,通过反射
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            //设置参数
            mBannerScroller = new BannerScroller(context);
            field.set(this, mBannerScroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mConvertViews = new HashSet<>();
    }

    /**
     * 设置viewPager切换速率
     *
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        this.mBannerScroller.setScrollDuration(scrollDuration);
    }

    /**
     * 设置viewPager条目适配器
     *
     * @param adapter
     */
    public void setAdapter(@Nullable IBannerAdapter adapter) {
        this.mAdapter = adapter;

        //设置父类ViewPager的Adapter
        mPagerAdapter = new BannerPagerAdapter();
        setAdapter(mPagerAdapter);

        mCount = mAdapter.getCount();
        if (mCount > 0) {
            if (startPosition == Integer.MAX_VALUE / 2) {
                startPosition = mCount * 5;
                int startIndex = startPosition % mCount;
                if (startIndex != 0) {
                    startPosition = startPosition + mCount - startIndex;
                }
            }
            setCurrentItem(startPosition);
        }

        // 注册Activity生命周期变化回调
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).getApplication().registerActivityLifecycleCallbacks(mActivityCallbacks);
        }
    }

    /**
     * 设置轮播起始位置
     *
     * @param startPosition
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
        setCurrentItem(startPosition);
    }

    /**
     * 开启自动轮播
     */
    public void start() {
        mIsStart = true;

        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);

        // 消息  延迟时间  让用户自定义  默认：3500
        if (mHandler == null)
            mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        // 移除Handler消息
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;

        // 解除注册Activity生命周期变化回调
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).getApplication().unregisterActivityLifecycleCallbacks(mActivityCallbacks);
        }
        super.onDetachedFromWindow();
    }

    public int getCurrentPosition() {
        if (mCount > 0) {
            return getCurrentItem() % mCount;
        } else {
            return 0;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * Banner适配器
     */
    private class BannerPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
//            为了实现无限轮询
            return mAdapter.getCount() == 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 创建条目回调
         *
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            // 为了让用户完全自定义，采用adapter设计模式
            final View bannerItemView = mAdapter.getView(position % mAdapter.getCount(), getConvertView());
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.itemClick(v, position % mAdapter.getCount());
                    }
                }
            });
            //添加ViewPager里面
            container.addView(bannerItemView);
            return bannerItemView;
        }

        /**
         * 销毁条目回调
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
            object = null;
        }
    }

    /**
     * 获取复用布局
     *
     * @return
     */
    private View getConvertView() {
        for (View convertView : mConvertViews) {
            if (convertView.getParent() == null) {
                return convertView;
            }

        }
        return null;
    }

    /**
     * 实现自动轮询 - 发送消息Handler
     */
    private static class MyHandler extends Handler {

        private final WeakReference<BannerViewPager> bannerWeak;

        public MyHandler(BannerViewPager banner) {
            bannerWeak = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            BannerViewPager banner = bannerWeak.get();
            if (banner != null) {
                switch (msg.what) {
                    case SCROLL_MSG:
                        banner.setCurrentItem(banner.getCurrentItem() + 1);
                        banner.start();
                        break;
                }
            }
        }
    }

    // 管理activity的生命周期
    Application.ActivityLifecycleCallbacks mActivityCallbacks = new RadishActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            super.onActivityResumed(activity);
            if (activity == getContext() && mIsStart) {
                //开启轮播
                if (mHandler == null)
                    mHandler = new MyHandler(BannerViewPager.this);
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            super.onActivityPaused(activity);

            if (activity == getContext() && mIsStart) {
                //禁止轮播
                if (mHandler != null)
                    mHandler.removeMessages(SCROLL_MSG);
            }
        }
    };

}
