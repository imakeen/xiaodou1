package com.radish.framelibrary.skin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.radish.baselibrary.base.BaseActivity;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.skin.attr.SkinAttr;
import com.radish.framelibrary.skin.attr.SkinView;
import com.radish.framelibrary.skin.callback.ISkinChangeListener;
import com.radish.framelibrary.skin.support.AppCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:36 AM
 * @邮箱 15703379121@163.com
 * @描述 换肤Activity基类
 */
public abstract class BaseSkinActivity extends BaseActivity implements ISkinChangeListener {
    private static final boolean IS_PRE_LOLLIPOP;
    AppCompatViewInflater mAppCompatViewInflater;

    static {
        IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
    }

    @Override
    protected void initSuperBefore() {
        LayoutInflater layoutInflater = LayoutInflater.from(BaseSkinActivity.this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
    }

    @Override
    protected void initLayoutAfter() {
        //判断是否需要换肤
        SkinManager.getInstance().checkChangeSkin(this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        // 拦截view的创建，获取view之后要去解析

        //1.创建view
        View view = createView(parent, name, context, attrs);

        //2.解析属性 src textColor background 自定义属性
        //2.1一个activity的布局对应多个SkinView
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            //3.统一交给SkinManager管理
            manageSkinView(skinView);

            //4.判断是否需要换肤
            SkinManager.getInstance().checkChangeSkin(skinView);
        }

        return view;
    }

    /**
     * 统一管理SkinView
     *
     * @param skinView
     */
    private void manageSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(this, skinViews);
        }
        skinViews.add(skinView);
    }

    /**
     * 创建view
     *
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @SuppressLint("RestrictedApi")
    public View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (this.mAppCompatViewInflater == null) {
            TypedArray a = this.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
            String viewInflaterClassName = a.getString(android.support.v7.appcompat.R.styleable.AppCompatTheme_viewInflaterClass);
            if (viewInflaterClassName != null && !android.support.v7.app.AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                try {
                    Class viewInflaterClass = Class.forName(viewInflaterClassName);
                    this.mAppCompatViewInflater = (AppCompatViewInflater) viewInflaterClass.getDeclaredConstructor().newInstance();
                } catch (Throwable var8) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + viewInflaterClassName + ". Falling back to default.", var8);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            } else {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            }
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = attrs instanceof XmlPullParser ? ((XmlPullParser) attrs).getDepth() > 1 : this.shouldInheritContext((ViewParent) parent);
        }

        return this.mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        } else {
            for (View windowDecor = this.getWindow().getDecorView(); parent != null; parent = parent.getParent()) {
                if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public void changeSkin(SkinResource skinResource) {
        //做一些第三方的改变
    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }
}
