package com.radish.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.radish.baselibrary.utils.LogUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:35 AM
 * @邮箱 15703379121@163.com
 * @描述 皮肤资源
 */
public class SkinResource {

    private Resources mSkinResources;
    private String mSkinPackageName;

    public SkinResource(Context context, String path) {

        try {
            if (context == null) {
                LogUtils.e("Context is null");
                return;
            }
            File file = new File(path);
            if (!file.exists()) {
                LogUtils.e("Skin File Path Is Null");
                return;
            }

            //创建AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, path);

            Resources superResources = context.getResources();
            mSkinResources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());

            PackageInfo skinPackage = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            if (skinPackage == null) {
                LogUtils.e("This File Not a Skin File");
                return;
            }
            mSkinPackageName = skinPackage.packageName;
            if (TextUtils.isEmpty(mSkinPackageName)) {
                LogUtils.e("This File Not a Skin File");
                return;
            }
            LogUtils.e("皮肤资源：" + mSkinResources + "   皮肤包名：" + mSkinPackageName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getSkinPackageName() {
        return mSkinPackageName;
    }

    /**
     * 通过资源名字获取drawable
     *
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mSkinPackageName);
            if (resId > 0) {
                Drawable drawable = mSkinResources.getDrawable(resId);
                return drawable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过资源名字获取dimen
     *
     * @param resName
     * @return
     */
    public int getDimenByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "dimen", mSkinPackageName);
            if (resId > 0) {
                int size = mSkinResources.getDimensionPixelSize(resId);
                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通过资源名字获取mipmap
     *
     * @param resName
     * @return
     */
    public Drawable getMipmapByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "mipmap", mSkinPackageName);
            if (resId > 0) {
                Drawable drawable = mSkinResources.getDrawable(resId);
                return drawable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过资源名字获取color
     *
     * @param resName
     * @return
     */
    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mSkinPackageName);
            if (resId > 0) {
                ColorStateList drawable = mSkinResources.getColorStateList(resId);
                return drawable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
