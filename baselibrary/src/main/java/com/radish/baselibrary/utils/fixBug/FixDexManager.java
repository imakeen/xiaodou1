package com.radish.baselibrary.utils.fixBug;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;

/**
 * @作者 radish
 * @创建日期 2018/11/29 3:50 PM
 * @邮箱 15703379121@163.com
 * @desc 热修复
 */
public class FixDexManager {

    private final Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        this.mDexDir = context.getDir("odex",Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {
//        1.先获取已经运行的DexElement
        ClassLoader classLoader = mContext.getClassLoader();
        Object dexElements = getDexElementByClassLoader(classLoader);


//        2.获取下载好的补丁的DexElement

//        2.1移动到系统能够访问到的 dex目录下  ClassLoader
        File srcFile = new File(fixDexPath);
        if (srcFile.exists()){
            throw  new FileNotFoundException(fixDexPath);
        }

        File targetFile = new File(mDexDir,srcFile.getName());
        if (targetFile.exists()){
            return;
        }


//        2.2ClassLoader读取fixDex的路径

//        3.把补丁的dexElement 插到已经运行的DexElement 的最前面
    }

    /**
     * 从classLoader中获取dexElement
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementByClassLoader(ClassLoader classLoader) throws Exception {
//        1.1先获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

//        1.2获取pathList里的dexElement
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        return dexElementsField.get(pathList);
    }
}
