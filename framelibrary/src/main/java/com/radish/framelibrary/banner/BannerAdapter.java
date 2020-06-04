package com.radish.framelibrary.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @作者 radish
 * @创建日期 2019/1/10 11:59 AM
 * @邮箱 15703379121@163.com
 * @描述 轮播图的图片Adapter
 */
public class BannerAdapter implements IBannerAdapter {
    private final Context mContext;
    private Object[] mImages;

    public BannerAdapter(Context context, Object... images) {
        this.mContext = context.getApplicationContext();
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public View getView(int position, View convertView) {
        if (convertView == null) {
            convertView = new ImageView(mContext);
            ((ImageView) convertView).setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Glide.with(mContext).load(mImages[position]).into((ImageView) convertView);
        return convertView;
    }
}
