package com.xinzu.xiaodou.pro.fragment.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.bean.backTimeBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.pro.activity.getcity.CityPickerActivity;
import com.xinzu.xiaodou.pro.activity.getcity.EnterPriseMapActivity;
import com.xinzu.xiaodou.pro.fragment.home.HomeContract;
import com.xinzu.xiaodou.pro.fragment.home.HomePresenter;
import com.xinzu.xiaodou.pro.fragment.home.PermissionListener;
import com.xinzu.xiaodou.pro.fragment.home.utils.Day;
import com.xinzu.xiaodou.pro.fragment.home.utils.PickerDailog;
import com.xinzu.xiaodou.util.SharedPreUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {
    private String title = "";

    public static MineFragment newInstance(String title) {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void initListener() {

    }


}
