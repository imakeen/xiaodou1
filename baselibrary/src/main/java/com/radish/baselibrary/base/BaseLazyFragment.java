package com.radish.baselibrary.base;

public abstract class BaseLazyFragment extends BaseFragment {

    protected boolean isVisible = false;
    @Override
    protected void initData() {
        //所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            loadData();
            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            initData();
        } else {
            isVisible = false;
        }
    }
}
