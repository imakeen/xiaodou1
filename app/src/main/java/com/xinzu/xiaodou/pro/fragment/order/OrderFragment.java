package com.xinzu.xiaodou.pro.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<OrderBean.OrderListBean> arrayList;

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
        getOrderList();
    }

    private void getOrderList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        hashMap.put("channelId", "4");
        hashMap.put("endIndex", String.valueOf(endIndex));
        hashMap.put("orderChannel", "1");
        hashMap.put("orderState", String.valueOf(title));
        hashMap.put("startIndex", String.valueOf(startIndex));
        hashMap.put("userId", SPUtils.getInstance().getString("userid"));
        mPresenter.userOrderList(new Gson().toJson(hashMap));
    }

    @OnClick({})
    public void onClick(View view) {

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
                refreshLayout.finishLoadMore(2000);
                startIndex = startIndex + 10;
                endIndex = endIndex + 10;
                getOrderList();
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
                startIndex = 0;
                endIndex = 9;
                arrayList.clear();
                getOrderList();

            }
        });
    }

    @Override
    public void getOrderList(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            if (1 == jsonObject.getInt("status")) {
                OrderBean orderBean = new Gson().fromJson(body, OrderBean.class);
                arrayList = new ArrayList<>();
                arrayList.addAll(orderBean.getOrderList());
                if (startIndex == 0) {
                    orderAdapter.setNewData(arrayList);
                } else {
                    orderAdapter.addData(arrayList);
                }
                orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        OrderDetails(arrayList.get(position).getOrderId());
                    }
                });
                orderAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrderDetails(String body) {
        if (!body.isEmpty()) {
            OrderdetailsBean bean = new Gson().fromJson(body, OrderdetailsBean.class);
            IntentUtils.getInstance().with(getActivity(), OrderDetailsActivity.class).putParcelable("bean", bean)
                    .start();
        }
    }

    private void OrderDetails(String details) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("timeStamp", SignUtils.temp());
        hashMap.put("orderCode", details);
        mPresenter.userOrderDetails(new Gson().toJson(hashMap));
    }
}
