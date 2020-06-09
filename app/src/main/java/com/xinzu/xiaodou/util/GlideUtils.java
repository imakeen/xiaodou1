package com.xinzu.xiaodou.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.radish.baselibrary.utils.LogUtils;

/**
 * Created by xiaoMan on 2016/9/30.
 * details:
 * Glide 工具类
 * Google推荐的图片加载库，专注于流畅的滚动
 * <p/>
 * Glide 比Picasso  加载快 但需要更大的内存来缓存
 * <p/>
 * Glide 不光接受Context，还接受Activity 和 Fragment ,图片加载会和Activity/Fragment的生命周期保持一致 在onPause（）暂停加载，onResume（）恢复加载
 * <p/>
 * 支持GIF格式图片加载
 * <p>
 * GlideUtils.getInstance().LoadContextBitmap(mContext, urlPath,iv, R.drawable.shop_placeholder_loading, R.drawable.shop_placeholder_loadfail, null);
 */

public class GlideUtils {

    private static GlideUtils instance;
    private static RequestOptions requestOptions;
    private @DrawableRes
    int imgDefault = com.radish.framelibrary.R.mipmap.ic_launcher;
    private @DrawableRes
    int imgCircleDefault = com.radish.framelibrary.R.mipmap.ic_launcher;

    public static GlideUtils getInstance() {
        if (instance == null) {
            synchronized (GlideUtils.class) {
                if (instance == null) {
                    instance = new GlideUtils();
                    requestOptions = new RequestOptions();
                }
            }
        }
        return instance;
    }

    /**
     * 普通加载展示图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public void loadPathImage(Context context, Object path, ImageView imageView) {
        try {
            Glide.with(context).load(path).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载gif图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public void loadGifImage(Context context, Object path, ImageView imageView) {
        try {
            requestOptions
                    .centerCrop();
            loadImage(context, path, imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void loadImage(Context context, Object path, ImageView imageView, RequestOptions requestOptions) {
        try {
            if (context != null) {
                Glide.with(context).load(path).apply(requestOptions).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImage(Context context, Object path, ImageView imageView) {
        try {
            if (context != null) {
                Glide.with(context).load(path).apply(requestOptions).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载普通图片，有展位图和错误图
     *
     * @param context
     * @param path
     * @param imageView
     */
    public void loadNormalImage(Context context, Object path, ImageView imageView) {
        try {
            requestOptions
                    .centerCrop()
                    .placeholder(imgDefault)
                    .error(imgDefault);
            Glide.with(context).load(path).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            LogUtils.e("e:" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public void loadCircleImage(final Context context, Object path, final ImageView imageView) {
        try {
            requestOptions
                    .circleCrop()
                    .placeholder(imgCircleDefault)
                    .error(imgCircleDefault);
            Glide.with(context).load(path).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}