package com.xinzu.xiaodou.pro;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.pro.fragment.home.HomeFragment;
import com.xinzu.xiaodou.pro.fragment.mine.MineFragment;
import com.xinzu.xiaodou.pro.fragment.order.OrderFragment;
import com.xinzu.xiaodou.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseGActivity {
    @BindView(R.id.vp_main)
    NoScrollViewPager mVpMain;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private ViewPagerAdapter mViewPagerAdapter;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initBundle() {

    }

    public void startLogin() {
        if (mVpMain != null && (mVpMain.getCurrentItem() == 2 || mVpMain.getCurrentItem() == 3)) {
            mVpMain.setCurrentItem(0);
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mVpMain.setNoScroll(true);

        fragments.add(HomeFragment.newInstance("首页"));
        fragments.add(OrderFragment.newInstance("订单"));
        fragments.add(MineFragment.newInstance("我的"));


        //除去自带效果
        bottomNavigation.setItemIconTintList(null);
        setImmersionBar();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mVpMain.setAdapter(mViewPagerAdapter);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutBefore() {
        super.initLayoutBefore();
        isUseImmersionBar = true;
    }

    @Override
    protected void initListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    isUseImmersionBar = false;
                    naviTab(0);
                    setImmersionBar();
                    break;
                case R.id.action_order:
                    isUseImmersionBar = false;
                    naviTab(1);
                    break;
                case R.id.action_mine:
                    isUseImmersionBar = false;
                    setImmersionBar();
                    naviTab(2);
                    break;
                default:
                    break;
            }
            return true;
        });

    }

    /**
     * 点击Navigation切换Tab
     *
     * @param position tab下标
     */
    private void naviTab(int position) {
        mVpMain.setCurrentItem(position, true);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> listTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> listTitle) {
            super(fm);
            this.mFragments = mFragments;
            this.listTitle = listTitle;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        public void recreateItems(List<Fragment> fragmentList, List<String> titleList) {
            this.mFragments = fragmentList;
            this.listTitle = titleList;
            notifyDataSetChanged();
        }
    }
}
