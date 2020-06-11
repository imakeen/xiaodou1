package com.xinzu.xiaodou.pro.activity.cartype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.pro.activity.registerlogin.RegisterActivity;
import com.xinzu.xiaodou.ui.activity.CarInfoActivity;
import com.xinzu.xiaodou.ui.adapter.CarAdapter;
import com.xinzu.xiaodou.ui.adapter.MenuAdapter;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.bean.CarGroupBean;
import com.xinzu.xiaodou.bean.getCarttypeBean;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CarTypeAcitvity extends BaseMvpActivity<CarTypePresenter> implements CarTypeContract.View {
    @BindView(R.id.lv_menu)
    RecyclerView lvMenu;
    @BindView(R.id.ll_car_type)
    LinearLayout ll_car_type;
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
    private String json;
    private getCarttypeBean carttypeBean;
    private Bundle bundle;
    private ArrayList<CarGroupBean.CarGroupListBean> arrayList;
    private ArrayList<CarBean.StoreListBean> carList;
    private String cityinfo;
    private String day;
    private String city;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        carttypeBean = bundle.getParcelable("cartype");
        cityinfo = bundle.getString("cityinfo");
        day = bundle.getString("day");
        city = bundle.getString("city");

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
        this.closeLoading();
        CarGroupBean carGroupBean = new Gson().fromJson(cartype, CarGroupBean.class);
        if (carGroupBean.getStatus() == 0) {
            ll_car_type.setVisibility(View.GONE);
        } else {
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
    }

    @Override
    public void getCar(String car) {
        this.closeLoading();
        CarBean carBean = new Gson().fromJson(car, CarBean.class);
        carList = new ArrayList<>();
        carList.addAll(carBean.getStoreList());
        carAdapter = new CarAdapter();
        lvHome.setAdapter(carAdapter);
        carAdapter.addData(carList);
        carAdapter.notifyDataSetChanged();
        carAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String userId = SPUtils.getInstance().getString("userid");
                if (userId.isEmpty()) {
                    startActivity(new Intent(CarTypeAcitvity.this, RegisterActivity.class));
                    finish();
                    return;
                }

                Intent intent = new Intent(CarTypeAcitvity.this, CarInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("carlist", carList.get(position));
                bundle.putString("city", city);
                bundle.putString("cityinfo", cityinfo);
                bundle.putString("day", day);
                bundle.putString("picktime", carttypeBean.getPickupDate());
                bundle.putString("returntime", carttypeBean.getReturnDate());
                intent.putExtra("bundle", bundle);
                ActivityUtils.startActivity(intent);

            }
        });
    }

}
