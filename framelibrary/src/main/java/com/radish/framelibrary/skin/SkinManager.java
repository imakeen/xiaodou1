package com.radish.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.skin.attr.SkinView;
import com.radish.framelibrary.skin.callback.ISkinChangeListener;
import com.radish.framelibrary.skin.config.SkinConfig;
import com.radish.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @作者 radish
 * @创建日期 2018/12/30 11:36 AM
 * @邮箱 15703379121@163.com
 * @描述 换肤管理
 */
public class SkinManager {

    private static SkinManager mInstance;
    private Context mContext;
    private SkinResource mSkinResource;
    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new HashMap<>();

    static {
        mInstance = new SkinManager();
    }

    private SkinManager() {
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();

//        验证是否有本地皮肤文件

        String currentSkinPath = SkinPreUtils.getInstance(context).getSkinPath();
        int hasFile = checkSkinFile(currentSkinPath);
        if (hasFile != SkinConfig.SKIN_CHANGE_SUCCESS) {
            return;
        }

        //初始化的工作
        mSkinResource = new SkinResource(context, currentSkinPath);
        if (TextUtils.isEmpty(mSkinResource.getSkinPackageName())) {
            LogUtils.i("换肤失败");
        }
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    /**
     * 更新皮肤
     *
     * @param path
     * @return
     */
    public int updateSkin(String path) {
        if (TextUtils.isEmpty(path)) {
            LogUtils.e("This Skin Path is Null");
            return SkinConfig.SKIN_CHANGE_FAIL;
        }

//        String localPath = SkinPreUtils.getInstance(mContext).getSkinPath();
//        if (TextUtils.equals(localPath, path)) {
//            LogUtils.e("Change Skin Path Equals Current Skin Path");
//            return SkinConfig.SKIN_CHANGE_SUCCESS;
//        }
        int isChange = loadSkin(path);

        if (isChange != SkinConfig.SKIN_CHANGE_SUCCESS) {
            return isChange;
        }
//        保存皮肤的状态
        saveSkinStatus(path);
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        String localPath = SkinPreUtils.getInstance(mContext).getSkinPath();

        int hasFile = checkSkinFile(localPath);
        if (hasFile != SkinConfig.SKIN_CHANGE_SUCCESS) {
            return hasFile;
        }

//        当前app的Apk资源路径
        String skinPath = mContext.getPackageResourcePath();
        loadSkin(skinPath);

//        清除皮肤状态
        clearSkinStatus();
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 加载皮肤
     *
     * @param changePath
     * @return
     */
    private int loadSkin(String changePath) {
//        校验签名

//        资源管理
        mSkinResource = new SkinResource(mContext, changePath);
        if (TextUtils.isEmpty(mSkinResource.getSkinPackageName())) {
            return SkinConfig.SKIN_CHANGE_FAIL;
        }
//        改变皮肤
        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }

            key.changeSkin(mSkinResource);
        }
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 验证此皮肤路径是否是正确的皮肤路径
     *
     * @param currentSkinPath
     * @return
     */
    private int checkSkinFile(String currentSkinPath) {
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_PATH_ERROR;
        }
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            clearSkinStatus();
            return SkinConfig.SKIN_CHANGE_FILE_NULL;
        }
        if (mContext.getPackageManager().getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES) == null) {
            clearSkinStatus();
            return SkinConfig.SKIN_CHANGE_FILE_ERROR;
        }
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 保存皮肤状态
     *
     * @param path
     */
    private void saveSkinStatus(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    /**
     * 删除皮肤状态
     */
    private void clearSkinStatus() {
        SkinPreUtils.getInstance(mContext).clearSkinInfo();
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     *
     * @param skinChangeListener
     * @param skinViews
     */
    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    /**
     * 获取当前资源管理
     *
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    /**
     * 检测是否需要换肤
     *
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(currentSkinPath)) {
            //换肤
            skinView.skin();
        }
    }

    public void checkChangeSkin(ISkinChangeListener key) {
        key.changeSkin(mSkinResource);
    }

    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }
}
