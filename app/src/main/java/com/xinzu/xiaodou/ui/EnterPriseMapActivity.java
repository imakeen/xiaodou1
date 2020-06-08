package com.xinzu.xiaodou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.adapter.CityAddapter;
import com.xinzu.xiaodou.base.BaseGActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EnterPriseMapActivity extends BaseGActivity implements PoiSearch.OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.rv_ct)
    RecyclerView recyclerView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    boolean isPoiSearched = false; //是否进行poi搜索
    private PoiSearch poiSearch;
    private PoiSearch.Query query;
    private double mCurrentLat;
    private double mCurrentLng;
    Map<String, String> currentInfo = new HashMap<>();
    List<PoiItem> poiItemArrayList = new ArrayList<>();

    CityAddapter cityAddapter;
    @BindView(R.id.et_seach)
    EditText et_serch;
    private String cityExtra;

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cityAddapter = new CityAddapter();
        recyclerView.setAdapter(cityAddapter);
        initAMap();
        Intent intent = getIntent();

        String city_title = intent.getStringExtra("city_title");
        cityExtra = intent.getStringExtra("city");
        if (city_title != null) {
            searchPoi(city_title, 0, currentInfo.get("cityCode"), false);
            GeocodeSearch(city_title);
        }
        if (cityExtra != null) {
            searchPoi(cityExtra, 0, currentInfo.get("cityCode"), false);
            GeocodeSearch(cityExtra);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void initListener() {
        et_serch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(EnterPriseMapActivity.this, SearchActivity.class);
                    intent.putExtra("city", cityExtra);
                    ActivityUtils.startActivity(intent);
                    finish();
                }
            }
        });
        cityAddapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String city = poiItemArrayList.get(position).getTitle();
                Intent intent = new Intent(EnterPriseMapActivity.this, SearchActivity.class);
                intent.putExtra("citytitle", city);
                intent.putExtra("city", cityExtra);
                ActivityUtils.startActivity(intent);
                finish();
            }
        });
    }

    public void GeocodeSearch(String city) {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        GeocodeQuery query = new GeocodeQuery(city, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                    && geocodeResult.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                LatLonPoint latLongPoint = address.getLatLonPoint();
                MarkerOptions mk = new MarkerOptions();
                mk.icon(BitmapDescriptorFactory.defaultMarker());
                LatLng ll = new LatLng(latLongPoint.getLatitude(), latLongPoint.getLongitude());
                mk.position(ll);
                //清除所有marker等，保留自身
                aMap.clear();
                myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
                aMap.animateCamera(CameraUpdateFactory.zoomTo(aMap.getMaxZoomLevel() - 1));
                aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
                myLocationStyle.interval(2000);
                aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
                CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
                aMap.animateCamera(cu);
                aMap.addMarker(mk);
            }
        }
    }

    void initAMap() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }

    void searchPoi(String key, int pageNum, String cityCode, boolean nearby) {
        isPoiSearched = true;
        query = new PoiSearch.Query(key, "", cityCode);
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageNum);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener((PoiSearch.OnPoiSearchListener) this);
        if (nearby)
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mCurrentLat,
                    mCurrentLng), 1500));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }

    public void onPoiSearched(PoiResult poiResult, int i) {
        int index = 0;
        //填充数据，并更新listview
        if (poiResult != null) {
            ArrayList<PoiItem> result = poiResult.getPois();
            if (result.size() > 0) {
                poiItemArrayList.clear();
                poiItemArrayList.addAll(result);
                cityAddapter.addData(poiItemArrayList);

                cityAddapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        et_serch.clearFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

}