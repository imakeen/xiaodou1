package com.xinzu.xiaodou.pro.fragment.mine;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.ui.activity.OpinionActivity;

import butterknife.OnClick;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {
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
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void initListener() {

    }

    @OnClick({R.id.my_opinion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_opinion:
                ActivityUtils.startActivity(OpinionActivity.class);
                break;
        }
    }

}
