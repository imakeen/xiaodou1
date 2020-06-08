package com.xinzu.xiaodou.pro.fragment.mine;

import android.os.Bundle;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;

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


}
