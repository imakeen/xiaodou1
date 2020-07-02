package com.xinzu.xiaodou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGLFragment;
import com.xinzu.xiaodou.pro.fragment.order.OrderFragment;
import com.xinzu.xiaodou.ui.adapter.MyFragmentPagerAdapter;
import com.xinzu.xiaodou.ui.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class OrdersFragment extends BaseGLFragment {
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.all_order_viewpager)
    ViewPager myViewPager;
    private Fragment[] fragmentList;
    private List<Fragment> fragments;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    String title = "";
    private List<String> myTitle;
    private MyPagerAdapter myPagerAdapter;

    public static OrdersFragment newInstance(String title) {
        Bundle args = new Bundle();
        OrdersFragment fragment = new OrdersFragment();
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_all_orders;
    }

    @Override
    protected void initView() {
        fragmentList = new Fragment[]{OrderFragment.newInstance(0), OrderFragment.newInstance(1), OrderFragment.newInstance(2),
                OrderFragment.newInstance(3)};
        fragments = new ArrayList<>();
        for (int i = 0; i < fragmentList.length; i++) {
            fragments.add(fragmentList[i]);
        }
        tabLayout.setupWithViewPager(myViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        String[] string = {"全部", "预约中", "行程中", "已完成"};
        myTitle = new ArrayList<>();
        for (int i = 0; i < string.length; i++) {
//            tabLayout.getTabAt(i).setText(string[i]);
            myTitle.add(string[i]);
        }
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, myTitle);
//        fragmentList = new Fragment[]{OrderFragment.newInstance(0), OrderFragment.newInstance(1), OrderFragment.newInstance(2),
//                OrderFragment.newInstance(3)};
//        myViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//
//                return fragmentList[i];
//            }
//
//            @Override
//            public int getCount() {
//                return fragmentList.length;
//            }
//
//            @Override
//            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            }
//        });
    }

    @Override
    protected void loadData() {

        myViewPager.setAdapter(myPagerAdapter);
//
//        tabLayout.setupWithViewPager(myViewPager);
//        String[] string = {"全部", "预约中", "行程中", "已完成"};
//        myTitle = new ArrayList<>();
//        for (int i = 0; i < string.length; i++) {
//            tabLayout.getTabAt(i).setText(string[i]);
//        }
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                OrderFragment.newInstance(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        Objects.requireNonNull(tabLayout.getTabAt(0)).select();
//        myViewPager.setCurrentItem(0);

    }

    @Override
    protected void initListener() {

    }
}
