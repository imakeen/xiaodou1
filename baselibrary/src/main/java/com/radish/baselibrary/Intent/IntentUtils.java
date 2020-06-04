package com.radish.baselibrary.Intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.radish.baselibrary.utils.LogUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @作者 radish
 * @创建日期 2018/11/20 6:08 PM
 * @邮箱 15703379121@163.com
 * @desc 用于Intent跳转的工具类
 */
public class IntentUtils {

    private Context mContext;
    private Intent mIntent;
    private static IntentUtils mInstance;

    private IntentUtils() {
    }

    public static IntentUtils getInstance() {
        if (mInstance == null) {
            synchronized (IntentUtils.class) {
                if (mInstance == null) {
                    mInstance = new IntentUtils();
                }
            }
        }
        return mInstance;
    }

    public IntentUtils with(Context context, Class clazz) {
        mContext = context;
        mIntent = new Intent(mContext, clazz);
        return this;
    }

    public IntentUtils putString(String key, String value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putInt(String key, int value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putInts(String key, int[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putBoolean(String key, boolean value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putBooleans(String key, boolean[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putBundle(String key, Bundle value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putLong(String key, long value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putLongs(String key, long[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putByte(String key, byte value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putBytes(String key, byte[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putChar(String key, char value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }


    public IntentUtils putChars(String key, char[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putDouble(String key, double value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putDoubles(String key, double[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putFloat(String key, float value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putFloats(String key, float[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putParcelable(String key, Parcelable value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putParcelables(String key, Parcelable[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putShort(String key, short value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putShorts(String key, short[] value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }

    public IntentUtils putSerializable(String key, Serializable value) {
        if (mIntent != null)
            mIntent.putExtra(key, value);
        return this;
    }


    public IntentUtils putBundle(Bundle value) {
        if (mIntent != null)
            mIntent.putExtras(value);
        return this;
    }

    public IntentUtils putListSerializable(String key, ArrayList<? extends Serializable> personList) {
        if (mIntent != null)
            mIntent.putExtra(key, personList);
        return this;
    }

    public IntentUtils putListParcelable(String key, ArrayList<? extends Parcelable> personList) {
        if (mIntent != null)
            mIntent.putParcelableArrayListExtra(key, personList);
        return this;
    }

    /**
     * 跳转页面
     *
     * @return
     */
    public void start() {
        if (mContext != null && mIntent != null) {
            if (mContext instanceof Activity) {
                ((Activity) mContext).startActivityForResult(mIntent,0);
            }else {
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);
            }

        } else {
            throw new NullPointerException();
        }
        release();
    }

    private void release() {
        this.mContext = null;
        this.mIntent = null;
    }


    /**
     * 带requestCode的跳转页面
     *
     * @param requestCode
     * @return
     */
    public void start(int requestCode) {
        if (mContext != null && mIntent != null) {
            if (mContext instanceof Activity) {
                ((Activity) mContext).startActivityForResult(mIntent, requestCode);
            } else {
                throw new IllegalArgumentException("请传递Activity级别的上下文");
            }
        } else {
            throw new NullPointerException();
        }
        release();
    }

    /**
     * 通过注解获取上一页面所传数据
     *
     * @param object
     * @throws IllegalAccessException
     */
    public static void init(Activity object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            IntentData intentData = field.getAnnotation(IntentData.class);
            if (intentData != null) {
                field.setAccessible(true);
                String key = intentData.value();
                if (TextUtils.isEmpty(key)) {
                    //没有传入key
                    key = field.getName();
                }
                Type type = field.getGenericType();
                String typeString = type.toString();
                Intent intent = object.getIntent();
                if (intent != null && !TextUtils.isEmpty(key) && intent.getExtras() != null) {
                    Object value = intent.getExtras().get(key);
                    if (typeString.contains("class android.os.Bundle") && value == null) {
                        value = intent.getExtras();
                    }
                    try {
                        if (value != null) {
                            field.set(object, value);
                        }
                    } catch (IllegalAccessException e) {
                        LogUtils.e("IntentUtil异常：" + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 通过注解获取上一页面所传数据
     *
     * @param object
     * @throws IllegalAccessException
     */
    public static void init(Fragment object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            IntentData intentData = field.getAnnotation(IntentData.class);
            if (intentData != null) {
                field.setAccessible(true);
                String key = intentData.value();
                if (TextUtils.isEmpty(key)) {
                    //没有传入key
                    key = field.getName();
                }
                Type type = field.getGenericType();
                String typeString = type.toString();
                Bundle arguments = object.getArguments();
                if (arguments != null && !TextUtils.isEmpty(key)) {
                    Object value = arguments.get(key);
                    if (typeString.contains("class android.os.Bundle") && value == null) {
                        value = arguments;
                    }
                    try {
                        if (value != null) {
                            field.set(object, value);
                        }
                    } catch (IllegalAccessException e) {
                        LogUtils.e("IntentUtils异常：" + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
