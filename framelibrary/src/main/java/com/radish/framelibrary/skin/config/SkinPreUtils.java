package com.radish.framelibrary.skin.config;

import android.content.Context;

/**
 * @作者 radish
 * @创建日期 2019/1/7 5:16 PM
 * @邮箱 15703379121@163.com
 * @描述 皮肤sp工具类
 */
public class SkinPreUtils {
    private static SkinPreUtils mInstance;
    private Context mContext;

    private SkinPreUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存皮肤路径
     *
     * @param skinPath
     */
    public void saveSkinPath(String skinPath) {
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .edit().putString(SkinConfig.SKIN_CONFIG_PATH_NAME, skinPath).commit();

    }

    /**
     * 获取当前皮肤路径
     *
     * @return
     */
    public String getSkinPath() {
        return mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_CONFIG_PATH_NAME, "");

    }

    /**
     * 清空皮肤信息
     */
    public void clearSkinInfo() {
        saveSkinPath("");

    }
}
