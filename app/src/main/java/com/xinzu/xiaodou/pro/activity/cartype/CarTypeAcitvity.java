package com.xinzu.xiaodou.pro.activity.cartype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.adapter.CarAdapter;
import com.xinzu.xiaodou.adapter.MenuAdapter;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.bean.CarGroupBean;
import com.xinzu.xiaodou.bean.getCarttypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeAcitvity extends BaseMvpActivity<CarTypePresenter> implements CarTypeContract.View {
    @BindView(R.id.lv_menu)
    RecyclerView lvMenu;
    @BindView(R.id.lv_home)
    RecyclerView lvHome;
    @BindView(R.id.tv_pick_city)
    TextView tvPickCity;
    @BindView(R.id.tv_pick_time)
    TextView tvPickTime;
    @BindView(R.id.Tian_Shu)
    TextView TianShu;
    @BindView(R.id.tv_return_city)
    TextView tvReturnCity;
    @BindView(R.id.tv_return_time)
    TextView tvReturnTime;

    private MenuAdapter menuAdapter;
    private CarAdapter carAdapter;
    private int currentItem;
    private List<Integer> showTitle = new ArrayList<>();
    private String json;
    private getCarttypeBean carttypeBean;
    private Bundle bundle;
    private ArrayList<CarGroupBean.CarGroupListBean> arrayList;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        carttypeBean = bundle.getParcelable("cartype");

        Gson gson = new Gson();
        json = gson.toJson(carttypeBean);

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_cartype;
    }

    @Override
    protected void initView() {
        tvPickTime.setText(carttypeBean.getPickupDate() + "");
        tvReturnTime.setText(carttypeBean.getReturnDate() + "");
        tvPickCity.setText(bundle.getString("city"));
        tvReturnCity.setText(bundle.getString("city"));
        lvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuAdapter = new MenuAdapter();
        lvMenu.setAdapter(menuAdapter);
        mPresenter.getcartype(json, this);

        lvHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        carAdapter = new CarAdapter();
        lvHome.setAdapter(carAdapter);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {
        menuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetChanged();
                carttypeBean.setCarGroupId(arrayList.get(position).getCarGroupId());
                Gson gson = new Gson();

                mPresenter.getcar(gson.toJson(carttypeBean), CarTypeAcitvity.this);
            }
        });
    }

    @Override
    public void getCarType(String cartype) {
        CarGroupBean carGroupBean = new Gson().fromJson(cartype, CarGroupBean.class);
        arrayList = new ArrayList<>();
        CarGroupBean.CarGroupListBean allbean = new CarGroupBean.CarGroupListBean();
        allbean.setCarGroupId(0);
        allbean.setCarGroupName("全部");
        arrayList.add(allbean);
        arrayList.addAll(carGroupBean.getCarGroupList());
        menuAdapter.addData(arrayList);
        menuAdapter.notifyDataSetChanged();

        carttypeBean.setCarGroupId(arrayList.get(0).getCarGroupId());
        Gson gson = new Gson();
        mPresenter.getcar(gson.toJson(carttypeBean), CarTypeAcitvity.this);
    }

    @Override
    public void getCar(String car) {
        CarBean carBean = new Gson().fromJson(car, CarBean.class);
        ArrayList<CarBean.StoreListBean> carList = new ArrayList<>();
        carList.addAll(carBean.getStoreList());
        carAdapter = new CarAdapter();
        lvHome.setAdapter(carAdapter);
        carAdapter.addData(carList);
        carAdapter.notifyDataSetChanged();
    }

}
