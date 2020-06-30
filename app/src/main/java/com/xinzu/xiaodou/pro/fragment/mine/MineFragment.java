package com.xinzu.xiaodou.pro.fragment.mine;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.radish.baselibrary.dialog.OnDialogViewClickListener;
import com.radish.baselibrary.dialog.RadishDialog;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.OnPermissionCallBack;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.ui.activity.AboutActivity;
import com.xinzu.xiaodou.ui.activity.JoinActivity;
import com.xinzu.xiaodou.ui.activity.OpinionActivity;
import com.xinzu.xiaodou.util.CommonOperate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    Unbinder unbinder;
    private String title = "";

    public static MineFragment newInstance(String title) {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initBundle() {
    }

    @Override
    public void onResume() {
        super.onResume();
        tvUserPhone.setText(SPUtils.getInstance().getString("phone").isEmpty() ? "小豆租车" : SPUtils.getInstance().getString("phone"));
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

        tvUserPhone.setText(SPUtils.getInstance().getString("phone").isEmpty() ? "小豆租车" : SPUtils.getInstance().getString("phone"));
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void initListener() {

    }

    @OnClick({R.id.setting, R.id.my_opinion, R.id.lianxi
            , R.id.ll_about, R.id.ll_jiameng})
    public void onClick(View view) {
//        if (view.getId() == R.id.setting) {
//            ActivityUtils.startActivity(SettingActivity.class);
//        }

        switch (view.getId()) {
            case R.id.my_opinion:
                ActivityUtils.startActivity(OpinionActivity.class);
                break;

            case R.id.lianxi:
                callphone();
                break;
            case R.id.ll_about:
                ActivityUtils.startActivity(AboutActivity.class);
                break;
            case R.id.ll_jiameng:
                ActivityUtils.startActivity(JoinActivity.class);
                break;
        }
    }

    public void callphone() {
        String phone = "400-6767-388";
        getPermission(new OnPermissionCallBack() {
            @Override
            public void permissionPass(String[] permissions) {
                new RadishDialog.Builder(getActivity()).setView(R.layout.dialog_alter_pink)
                        .setText(R.id.dialog_message, "即将拨打电话" + phone)
                        .setText(R.id.dialog_submit, "立即拨打")
                        .setText(R.id.dialog_cancel, "取消拨打")
                        .setClick(R.id.dialog_submit, new OnDialogViewClickListener() {
                            @Override
                            public void onClick(final RadishDialog dialog, View view) {
                                CommonOperate.callPhone(MineFragment.this, phone);
                                dialog.dismiss();
                            }
                        })
                        .setClick(R.id.dialog_cancel, new OnDialogViewClickListener() {
                            @Override
                            public void onClick(RadishDialog dialog, View view) {
                                dialog.dismiss();
                            }
                        }).show();
            }

            @Override
            public void permissionRefuse(String[] permissions) {

            }
        }, Manifest.permission.CALL_PHONE);
    }
}
