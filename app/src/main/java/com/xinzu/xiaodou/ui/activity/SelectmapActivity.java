package com.xinzu.xiaodou.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.pro.activity.city.CityListenter;
import com.xinzu.xiaodou.ui.adapter.CityAddapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectmapActivity extends BaseGActivity {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.rv_ct)
    RecyclerView recyclerView;
    @BindView(R.id.et_seach)
    EditText et_serch;
    private CityAddapter cityAddapter;
    private AMap aMap;
    private String cityExtra;
    private String citycode;
    private String city_title;
    private LatLonPoint latLongPoint;
    List<PoiItem> poiItemArrayList = new ArrayList<>();
    boolean isOnCameraChangeListener = false;
    private PoiItem firstItem;
    private LatLonPoint searchLatlonPoint;
    private Marker locationMarker;


    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        city_title = intent.getStringExtra("city_title");
        citycode = intent.getStringExtra("citycode");
        cityExtra = intent.getStringExtra("city");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cityAddapter = new CityAddapter();
        recyclerView.setAdapter(cityAddapter);
        //将传递过来的城市显示到地图上
        if (!isOnCameraChangeListener) {
            GeocodeSearch(cityExtra);
            searchPoi(cityExtra, "", false);
        }
    }

    @Override
    protected void initTitle() {

    }

    //将城市转化为经纬度
    public void GeocodeSearch(String city) {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                            && geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                        latLongPoint = address.getLatLonPoint();
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLongPoint.getLatitude(), latLongPoint.getLongitude()), 16f));
                    }
                }
            }
        });
        GeocodeQuery query = new GeocodeQuery(city, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    //通过经纬度获得地址
    private void RegeocodeSearched() {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getRegeocodeAddress() != null
                            && result.getRegeocodeAddress().getFormatAddress() != null) {
                        String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
                        searchPoi(address, "", false);
                    }
                } else {
                    ToastUtils.showShort("error code is " + rCode);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });

        RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocodeSearch.getFromLocationAsyn(query);
    }

    //进行poi搜索
    void searchPoi(String key, String cityCode, boolean nearby) {
        PoiSearch.Query query = new PoiSearch.Query(key, "", cityCode);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (isOnCameraChangeListener)
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1500, true));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (poiResult != null) {
                    ArrayList<PoiItem> result = poiResult.getPois();
                    if (result.size() > 0) {
                        poiItemArrayList.clear();
                        poiItemArrayList.addAll(result);
                        cityAddapter.setNewData(poiItemArrayList);
                        cityAddapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
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
        //将坐标固定到地图中央
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });
        //拖到地图时的监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                isOnCameraChangeListener = true;
                if (isOnCameraChangeListener) {
                    searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    LogUtils.e(cameraPosition.target.latitude + "================" + searchLatlonPoint.getLongitude());
                    RegeocodeSearched();
                }
            }
        });
        et_serch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(SelectmapActivity.this, SearchActivity.class);
                    intent.putExtra("city", cityExtra);
                    intent.putExtra("citycode", citycode);
                    ActivityUtils.startActivity(intent);
                    et_serch.clearFocus();
                    finish();
                }
            }
        });
        cityAddapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CityListenter cityListenter = new CityListenter();

                cityListenter.listener.select(cityExtra +
                                cityAddapter.getItem(position).getDirection() +
                                cityAddapter.getItem(position).getTitle(), cityExtra
                        , citycode,
                        poiItemArrayList.get(position).getLatLonPoint().getLongitude() + ""
                        , poiItemArrayList.get(position).getLatLonPoint().getLatitude() + ""
                );
                finish();
            }
        });
    }


    //将地图maker固定到地图中央
    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}