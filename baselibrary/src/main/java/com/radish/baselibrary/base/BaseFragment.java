package com.radish.baselibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected boolean isInitView = false;
    Activity mActivity;
    protected View mView;
    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBundle();
        mView = inflater.inflate(initLayout(), container, false);

        initLayoutAfter();

        initView();

        isInitView = true;

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initViewCreated();

        initData();

        initListener();
    }

    protected void initViewCreated(){}

    protected abstract void initBundle();

    protected abstract int initLayout();

    protected void initLayoutAfter() {
    }


    protected void startActivity(Class<?> clazz) {
        intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
    protected abstract void initView();

    protected void initData() {
        loadData();
    }

    protected abstract void loadData();

    protected abstract void initListener();

}
