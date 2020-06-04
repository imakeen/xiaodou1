package com.radish.framelibrary.skin.attr;

import android.view.View;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:46 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class SkinAttr {
    private String mResName;
    private SkinType mType;

    public SkinAttr(String resName, SkinType type) {
        this.mResName = resName;
        this.mType = type;
    }

    public void skin(View view) {
        mType.skin(view, mResName);
    }

}
