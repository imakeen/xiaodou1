package com.xinzu.xiaodou.pro.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.radish.baselibrary.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.util.CommonUtil;

import butterknife.BindView;


/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class OrderFragment extends BaseMvpFragment<OrderPresenter> implements OrderContract.View {
    private String title = "";
    @BindView(R.id.bt_all)
    Button bt_all;
    @BindView(R.id.bt_yuyue)
    Button bt_yeyue;
    @BindView(R.id.bt_xingcheng)
    Button bt_xc;
    @BindView(R.id.bt_yiwancheng)
    Button bt_ywc;

    @BindView(R.id.srl)
    SmartRefreshLayout smartRefreshLayout;

    public static OrderFragment newInstance(String title) {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }

//    @BindView(R.id.tv)
//    TextView tv;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initBundle() {


    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_orders;
    }

    @Override
    protected void initView() {
        bt_all.setSelected(true);
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initListener() {
        RefreshLayout();
    }

    @Override
    public void updateUI(String body) {
        LogUtils.e("更新UI" + body);

    }

    /**
     * 上拉刷新下拉加载
     *
     * @time 2019/10/9 17:43
     */
    private void RefreshLayout() {
        CommonUtil.initRefresh(getActivity(), smartRefreshLayout);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

}
