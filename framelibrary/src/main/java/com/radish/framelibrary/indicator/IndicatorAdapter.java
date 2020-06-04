package com.radish.framelibrary.indicator;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @作者 radish
 * @创建日期 2019/1/17 2:57 PM
 * @邮箱 15703379121@163.com
 * @描述 默认的ViewPager适配器
 */
public class IndicatorAdapter extends BaseIndicatorAdapter {

    protected String[] mItems;
    protected Context mContext;

    public IndicatorAdapter(Context context, String... titles) {
        this.mItems = titles;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.length;
    }

    @Override
    public View getView(int position, ViewGroup parent) {
        TextView colorTrackTextView = new TextView(mContext);
        colorTrackTextView.setTextSize(getOriginTextSize());
        colorTrackTextView.setTextColor(getOriginTextColor());
        colorTrackTextView.setGravity(Gravity.CENTER);
        colorTrackTextView.setText(mItems[position]);
        return colorTrackTextView;
    }

    @Override
    public void highLightIndicator(View view, int position) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(getChangeTextColor());
            ((TextView) view).setTextSize(getChangeTextSize());
            view.setSelected(true);
        }
    }

    @Override
    public void restoreIndicator(View view, int position) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(getOriginTextColor());
            ((TextView) view).setTextSize(getOriginTextSize());
            view.setSelected(false);
        }

    }

    @Override
    public View getBottomTrackView() {
        View view = new View(mContext);
        view.setBackgroundColor(Color.parseColor("#018CAC"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8);
        view.setLayoutParams(params);
        return view;
    }
}
