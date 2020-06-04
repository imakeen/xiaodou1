package com.radish.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:40 AM
 * @邮箱 15703379121@163.com
 * @描述
 */
public class SkinView {
    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }
}
