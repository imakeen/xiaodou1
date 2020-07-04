package com.xinzu.xiaodou.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGLFragment;
import com.xinzu.xiaodou.pro.fragment.order.OrderFragment;
import com.xinzu.xiaodou.ui.adapter.MyFragmentPagerAdapter;
import com.xinzu.xiaodou.ui.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

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
            myTitle.add(string[i]);
        }
    }

    @Override
    protected void loadData() {
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, myTitle);
        myViewPager.setAdapter(myPagerAdapter);
    }

    @Override
    protected void initListener() {

    }
}
