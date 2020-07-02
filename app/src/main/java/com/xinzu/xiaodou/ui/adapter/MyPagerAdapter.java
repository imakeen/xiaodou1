package com.xinzu.xiaodou.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<String> slist;


    public MyPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> slist) {
        super(fm);
        this.list = list;
        this.slist = slist;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = list.get(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return slist.size();
    }

    @Nullable
    @Override
    //返回标题,返回给tab
    public CharSequence getPageTitle(int position) {
        return slist.get(position);
    }
}
