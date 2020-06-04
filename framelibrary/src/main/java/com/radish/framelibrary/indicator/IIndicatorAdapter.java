package com.radish.framelibrary.indicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * @作者 radish
 * @创建日期 2019/1/17 2:13 PM
 * @邮箱 15703379121@163.com
 * @描述 指示器的adapter
 */
public interface IIndicatorAdapter {

    // 获取总共的显示条数
    int getCount();

    // 根据当前位置获取View
    View getView(int position, ViewGroup parent);

    // 高亮当前位置
    void highLightIndicator(View view, int position);

    // 重置当前位置
    void restoreIndicator(View view, int position);

    // 添加底部跟踪指示器
    View getBottomTrackView();

    // 设置文字原颜色

    // 设置文字变化颜色
}
