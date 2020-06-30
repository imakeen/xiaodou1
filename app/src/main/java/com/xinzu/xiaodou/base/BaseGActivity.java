package com.xinzu.xiaodou.base;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.amap.api.maps2d.MapView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.base.BaseActivity;
import com.radish.baselibrary.navigationbar.DefaultNavigationBar;
import com.radish.baselibrary.utils.PermissionsUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseView;
import com.xinzu.xiaodou.util.CommonUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * <pre>
 *     author : radish
 *     e-mail : 15703379121@163.com
 *     time   : 2019/4/16
 *     desc   :
 * </pre>
 */
public abstract class BaseGActivity extends BaseActivity implements BaseView {

    //状态栏工具
    protected ImmersionBar mImmersionBar;
    protected ProgressDialog progressDialog;
    protected boolean isUseImmersionBar = false;
    protected boolean isImmersion = true;
    private OnPermissionCallBack mPermissionCallBack;
    protected boolean isEventBus = false;
    protected KProgressHUD mKProgressHUD;
    protected LinearLayout llRoot;
    private MapView mapView;


    @Override
    protected void initLayoutAfter() {
        super.initLayoutAfter();
        if (isEventBus) {
            EventBus.getDefault().register(this);
        }
        if (isImmersion) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();
            //防止设置状态栏后  软键盘怎么设置也不会把布局顶上去
            mImmersionBar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).navigationBarWithKitkatEnable(false).init();
            if (isUseImmersionBar) {
                setImmersionBar();
            } else {
                setStatusBar();
            }
        } else {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        ButterKnife.bind(this);
        IntentUtils.init(this);
    }

    @Override
    public void onBackPressed() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            return;
        }
        super.onBackPressed();
        finish();
    }

    public void setImmersionBar() {
        mImmersionBar
                .fitsSystemWindows(false)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.transparent)
                .navigationBarColor(R.color.black)
                .fullScreen(false)
                .statusBarDarkFont(false)
                .init();
    }

    /**
     * 设置状态栏
     */
    public void setStatusBar() {
        mImmersionBar
                .fitsSystemWindows(true, R.color.colororange)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.colororange)
                .navigationBarColor(R.color.black)
                .statusBarDarkFont(false)
                .fullScreen(false)
                .init();
    }

    public void setStatusBarColor() {
        mImmersionBar
                .fitsSystemWindows(true, R.color.white
                )
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.white)
                .navigationBarColor(R.color.black)
                .statusBarDarkFont(true)
                .fullScreen(false)

                .init();
    }


    public void getPermission(final OnPermissionCallBack callBack, final String... permissions) {
        this.mPermissionCallBack = callBack;
        PermissionsUtils.getInstance().checkPermissions(this, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissions() {
                if (callBack != null) {
                    callBack.permissionPass(permissions);
                }
            }

            @Override
            public void refusePermissions() {
                if (callBack != null) {
                    callBack.permissionRefuse(permissions);
                }
            }
        }, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length != 0) {
            boolean permission = true;
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    permission = false;
                    break;
                }
            }
            if (mPermissionCallBack != null) {
                if (permission) {
                    mPermissionCallBack.permissionPass(permissions);
                } else {
                    mPermissionCallBack.permissionRefuse(permissions);
                }
            }
        }
    }

    protected DefaultNavigationBar initTitle(String title) {
        return new DefaultNavigationBar.Builder(this, llRoot).setTitle(CommonUtil.getStr(title)).builder();
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.hideSoftInput(this);
        if (isEventBus) {
            EventBus.getDefault().unregister(this);
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        super.onDestroy();
       //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @Override
    public void showLoading() {
        if (mKProgressHUD == null || !mKProgressHUD.isShowing()) {
            mKProgressHUD = KProgressHUD.create(this);
            mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }


    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {
        ToastUtils.showShort("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showShort("请检查网络是否连接");
    }

    @Override
    public void onReLoad() {

    }

}
