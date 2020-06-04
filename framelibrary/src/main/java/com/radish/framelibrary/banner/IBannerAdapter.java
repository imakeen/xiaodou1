package com.radish.framelibrary.banner;

import android.view.View;

/**
 * @作者 radish
 * @创建日期 2019/1/10 11:47 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public interface IBannerAdapter {

    /**
     * 设置banner条目数
     *
     * @return
     */
    int getCount();

    /**
     * 获取ViewPager的自条目view
     *
     * @param position
     * @param convertView
     * @return
     */
    View getView(int position, View convertView);
}
