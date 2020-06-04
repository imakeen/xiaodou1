package com.xinzu.xiaodou.pro.activity.getcity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.adapter.CityAddapter;
import com.xinzu.xiaodou.base.BaseGActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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
    ;
    private MyHandler myHandler;
    CityAddapter cityAddapter;

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
        initAMap();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        myHandler = new MyHandler();
        searchPoi(city, 0, currentInfo.get("cityCode"), false);
        GeocodeSearch(city);
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

    public void GeocodeSearch(String city) {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
//通过GeocodeQuery设置查询参数,调用getFromLocationNameAsyn(GeocodeQuery geocodeQuery) 方法发起请求。
//address表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode都ok
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

                //获取到的经纬度
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
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
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
                cityAddapter = new CityAddapter(poiItemArrayList);
                recyclerView.setAdapter(cityAddapter);
                myHandler.sendEmptyMessage(0x001);
            }
        }
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    //加载listview中数据
                    cityAddapter.notifyDataSetChanged();
                    break;
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