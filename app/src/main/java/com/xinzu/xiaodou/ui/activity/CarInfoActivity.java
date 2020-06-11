package com.xinzu.xiaodou.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.util.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/6/10 11:16
 */
public class CarInfoActivity extends BaseGActivity {

    @BindView(R.id.car_image)
    ImageView carImage;
    @BindView(R.id.tv_vehicleName)
    TextView tvVehicleName;
    @BindView(R.id.tv_displacement)
    TextView tvDisplacement;
    @BindView(R.id.tv_pick_time)
    TextView tvPickTime;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_return_time)
    TextView tvReturnTime;
    @BindView(R.id.tv_pick_city)
    TextView tvPickCity;
    @BindView(R.id.tv_pick_city_info)
    TextView tvPickCityInfo;
    @BindView(R.id.tv_return_city)
    TextView tvReturnCity;
    @BindView(R.id.tv_return_city_info)
    TextView tvReturnCityInfo;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_edit_user)
    TextView tvEditUser;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    private CarBean.StoreListBean bean;
    private String cityinfo;
    private String day;
    private String city;
    private String picktime;
    private String returntime;

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        bean = bundle.getParcelable("carlist");
        cityinfo = bundle.getString("cityinfo");
        day = bundle.getString("day");
        city = bundle.getString("city");
        picktime = bundle.getString("picktime");
        returntime = bundle.getString("returntime");

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initView() {
        GlideUtils.getInstance().loadPathImage(this, bean.getImage(), carImage);
        tvVehicleName.setText(bean.getVehicleName());
        tvDisplacement.setText(bean.getDisplacement());
        tvPickCity.setText(city);
        tvPickTime.setText(picktime);
        tvReturnTime.setText(returntime);
        tvReturnCity.setText(city);
        tvPickCityInfo.setText(cityinfo);
        tvReturnCityInfo.setText(cityinfo);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_user, R.id.bt_usercar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_usercar:
                if (tvUser.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请选择司机");
                    return;
                }
                break;
        }
    }


}
