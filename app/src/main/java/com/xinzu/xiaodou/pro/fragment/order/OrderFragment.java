package com.xinzu.xiaodou.pro.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.radish.baselibrary.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.bean.OrderBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.ui.adapter.OrderAdapter;
import com.xinzu.xiaodou.util.CommonUtil;
import com.xinzu.xiaodou.util.KHMD5;
import com.xinzu.xiaodou.util.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int startIndex = 0;
    private int endIndex = 9;
    private int orderState = 0;

    public static OrderFragment newInstance(String title) {

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
        bt_all.setSelected(true);
    }

    @Override
    protected void loadData() {
        getOrderList();
    }

    private void getOrderList() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("appKey", ApiService.appKey);
        hashMap.put("channelId", "4");
        hashMap.put("endIndex", String.valueOf(endIndex));
        hashMap.put("orderChannel", "1");
        hashMap.put("orderState", String.valueOf(orderState));
        hashMap.put("sign", SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        hashMap.put("startIndex", String.valueOf(startIndex));
        hashMap.put("timeStamp", SignUtils.temp());
        hashMap.put("userId", SPUtils.getInstance().getString("userid"));
        mPresenter.userOrderList(new Gson().toJson(hashMap));
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
                ArrayList<OrderBean.OrderListBean> arrayList = new ArrayList<>();
                arrayList.addAll(orderBean.getOrderList());
                OrderAdapter orderAdapter = new OrderAdapter();
                orderAdapter.addData(arrayList);
                orderAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(orderAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
