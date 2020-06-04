package com.radish.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.skin.attr.SkinAttr;
import com.radish.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:36 AM
 * @邮箱 15703379121@163.com
 * @描述 皮肤属性解析支持类
 */
public class SkinAttrSupport {
    /**
     * 获取SkinAttrs的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
//        background src textColor
        List<SkinAttr> skinAttrs = new ArrayList<>();

        int attrLength = attrs.getAttributeCount();
        for (int i = 0; i < attrLength; i++) {
            //获取名称
            String attrName = attrs.getAttributeName(i);
            if (!TextUtils.isEmpty(attrName)) {
                SkinType skinType = getSkinType(attrName);
                if (skinType != null) {
                    String resName = getResName(context, attrs.getAttributeValue(i));
                    if (TextUtils.isEmpty(resName)) {
                        continue;
                    }
                    SkinAttr skinAttr = new SkinAttr(resName, skinType);
                    skinAttrs.add(skinAttr);
                }
            }
        }
        return skinAttrs;
    }

    /**
     * 获取资源的名称
     *
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@") && !TextUtils.equals(attrValue,"@0") && !TextUtils.equals(attrValue,"@null")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);

            return context.getResources().getResourceEntryName(resId);
        }

        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (attrName.equals(skinType.getResName())) {
                return skinType;
            }
        }
        return null;
    }
}
