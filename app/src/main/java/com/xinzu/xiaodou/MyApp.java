package com.xinzu.xiaodou;

import com.blankj.utilcode.util.LogUtils;
import com.radish.baselibrary.base.BaseApplication;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinzu.xiaodou.dragger.component.DaggerMyAppComponent;
import com.xinzu.xiaodou.dragger.component.MyAppComponent;
import com.xinzu.xiaodou.dragger.module.MyAppModule;
import com.xinzu.xiaodou.http.HttpManager;
import com.xinzu.xiaodou.util.CommonUtil;

/**
 * usreid 18
 */
public class MyApp extends BaseApplication {
    private HttpManager mHttpManager = null;
    public static IWXAPI api;

    @Override
    protected void init() {
        //初始化网络
        mHttpManager = new HttpManager();

        initWechat();

    }

    /**
     * 注册微信
     */
    private void initWechat() {
        api = WXAPIFactory.createWXAPI(getApplicationContext(), CommonUtil.WECHAT_APPID);
        boolean b = api.registerApp(CommonUtil.WECHAT_APPID);
        LogUtils.i("注册微信结果=" + b);
    }

//    private void initBugly() {
//        Bugly.init(getApplicationContext(), "61a6e39c71", true);
//        Beta.autoDownloadOnWifi = true;
//        Beta.autoInit = true;
//        Beta.autoCheckUpgrade = true;
//        Beta.enableHotfix = true;
//    }

    /**
     * 关联apiService
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T apiService(Class<T> clz) {
        return (getInstance()).mHttpManager.getService(clz);
    }

    public static synchronized MyApp getInstance() {
        return (MyApp) mInstance;
    }


    public static MyAppComponent getAppComponent() {
        return DaggerMyAppComponent.builder()
                .myAppModule(new MyAppModule(getInstance()))
                .build();
    }




}
