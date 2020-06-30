package com.xinzu.xiaodou.base;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.base.BaseLazyFragment;
import com.xinzu.xiaodou.base.mvp.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : radish
 *     e-mail : 15703379121@163.com
 *     time   : 2019/4/16
 *     desc   :
 * </pre>
 */
public abstract class BaseGLFragment extends BaseLazyFragment implements BaseView {

    private Unbinder unbinder;    protected KProgressHUD mKProgressHUD;


    @Override
    protected void initLayoutAfter() {
        IntentUtils.init(this);
        unbinder = ButterKnife.bind(this, mView);
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getPermission(final OnPermissionCallBack callBack, final String... permissions) {
        Activity activity = getActivity();
        if (activity instanceof BaseGActivity) {
            ((BaseGActivity) activity).getPermission(callBack,permissions);
        }
    }

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(getActivity());
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
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
