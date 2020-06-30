package com.xinzu.xiaodou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.ui.adapter.CityAddapter;
import com.xinzu.xiaodou.base.BaseGActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseGActivity {
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
    private static Slelect slelect;
    private String city;
    private String citycode;

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        citycode = intent.getStringExtra("citycode");
        searchPoi(intent.getStringExtra("citytitle"), 0, currentInfo.get("cityCode"), false);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_search_city;
    }

    @Override
    protected void initView() {
        setStatusBarColor();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cityAddapter = new CityAddapter();
        recyclerView.setAdapter(cityAddapter);
        initAMap();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        et_serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchPoi(s.toString(), 0, currentInfo.get("cityCode"), false);
            }
        });

    }

    @OnClick({R.id.city_canel})
    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        finish();
    }

    void initAMap() {
        //初始化地图控制器对象

    }

    public void setselect(Slelect select) {
        this.slelect = select;
    }

    private void searchPoi(String key, int pageNum, String cityCode, boolean nearby) {
        isPoiSearched = true;
        query = new PoiSearch.Query(key, "", cityCode);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageNum);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                int index = 0;
                //填充数据，并更新listview
                if (poiResult != null) {
                    ArrayList<PoiItem> result = poiResult.getPois();
                    if (result.size() > 0) {
                        poiItemArrayList.clear();
                        poiItemArrayList.addAll(result);
                        recyclerView.setAdapter(cityAddapter);
                        cityAddapter.setNewData(poiItemArrayList);
                        cityAddapter.notifyDataSetChanged();
                        cityAddapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                slelect.select( poiItemArrayList.get(position).getProvinceName()+

                                                poiItemArrayList.get(position).getTitle(), city == null ?
                                                poiItemArrayList.get(position).getCityName() : city
                                        , citycode,
                                        poiItemArrayList.get(position).getLatLonPoint().getLongitude() + ""
                                        , poiItemArrayList.get(position).getLatLonPoint().getLatitude() + ""
                                );
                                finish();
                            }
                        });
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        if (nearby)
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mCurrentLat,
                    mCurrentLng), 1500));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }


    public interface Slelect {
        void select(String city_title, String city, String citycode,
                    String pickuplongitude,
                    String pickuplatitude);
    }
}