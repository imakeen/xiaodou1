package com.xinzu.xiaodou.pro.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.radish.baselibrary.Intent.IntentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.bean.OrderBean;
import com.xinzu.xiaodou.bean.OrderdetailsBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.ui.activity.OrderDetailsActivity;
import com.xinzu.xiaodou.ui.adapter.OrderAdapter;
import com.xinzu.xiaodou.util.CommonUtil;
import com.xinzu.xiaodou.util.SignUtils;
import com.xinzu.xiaodou.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class OrderFragment extends BaseMvpFragment<OrderPresenter> implements OrderContract.View {
    private int title;
    @BindView(R.id.srl)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int startIndex = 0;
    private int endIndex = 9;

    private OrderAdapter orderAdapter;
    private String orderid;


    public static OrderFragment newInstance(int title) {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
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
        return R.layout.fragment_orders;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderAdapter();
        recyclerView.addItemDecoration(new SpaceItemDecoration(0, 20));
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderList();
    }

    private void getOrderList() {

        Hashtable<String, String> hashMap = new Hashtable<>();
        hashMap.put("appKey", ApiService.appKey);
        String temp = SignUtils.temp();
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("channelId", "4");
        hashMap.put("endIndex", String.valueOf(endIndex));
        hashMap.put("orderChannel", "1");
        hashMap.put("orderState", String.valueOf(title));
        hashMap.put("startIndex", String.valueOf(startIndex));
        hashMap.put("userId", SPUtils.getInstance().getString("userid"));
        mPresenter.userOrderList(new Gson().toJson(hashMap), getActivity());
    }


    @Override
    protected void initListener() {
        RefreshLayout();
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
                startIndex = startIndex + 10;
                endIndex = endIndex + 10;
                getOrderList();
                refreshLayout.finishLoadMore(1000);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                startIndex = 0;
                endIndex = 9;

                getOrderList();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void getOrderList(String body) {
        OrderBean orderBean = new Gson().fromJson(body, OrderBean.class);
        if (orderBean == null || orderBean.getOrderList() == null
                || orderBean.getOrderList().size() == 0) {
            if (startIndex == 0) {
                recyclerView.setVisibility(View.GONE);

            } else {
                ToastUtils.showShort("暂无更多数据");
            }
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        if (startIndex == 0) {
            orderAdapter.setNewData(orderBean.getOrderList());
        } else {
            orderAdapter.addData(orderBean.getOrderList());
        }
        orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                orderid = orderAdapter.getData().get(position).getOrderId();
                OrderDetails(orderid);
            }
        });
        orderAdapter.notifyDataSetChanged();

    }

    @Override
    public void getOrderDetails(String body) {
        LogUtils.e(body);
        if (!body.isEmpty()) {
            OrderdetailsBean bean = new Gson().fromJson(body, OrderdetailsBean.class);
            if (bean != null && bean.getIdNo() != null)
                IntentUtils.getInstance().with(getActivity(), OrderDetailsActivity.class).putParcelable("bean", bean)
                        .putString("orderid", orderid)
                        .start();
            else ToastUtils.showShort("签名错误，请重试");
        }
    }

    private void OrderDetails(String details) {
        Hashtable<String, String> hashMap = new Hashtable<>();
        hashMap.put("appKey", ApiService.appKey);
        String temp = SignUtils.temp();
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", temp));
        hashMap.put("timeStamp", temp);
        hashMap.put("orderCode", details);
        mPresenter.userOrderDetails(new Gson().toJson(hashMap), getActivity());
    }
}
