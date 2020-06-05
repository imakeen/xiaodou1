package com.xinzu.xiaodou.pro.fragment.order;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.pro.fragment.home.HomeContract;
import com.xinzu.xiaodou.pro.fragment.home.HomePresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

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

    }

    @Override
    public void updateUI(String body) {
        LogUtils.e("更新UI" + body);

    }


}
