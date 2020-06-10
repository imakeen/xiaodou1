package com.xinzu.xiaodou.pro.activity.city;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.ui.adapter.CityListAdapter;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;
import com.xinzu.xiaodou.bean.City;
import com.xinzu.xiaodou.bean.CityPickerBean;
import com.xinzu.xiaodou.bean.LocateState;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.ui.activity.EnterPriseMapActivity;
import com.xinzu.xiaodou.util.PinyinUtils;
import com.xinzu.xiaodou.util.SignUtils;
import com.xinzu.xiaodou.view.SideLetterBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import butterknife.OnClick;

public class CityPickerActivity extends BaseMvpActivity<CityPickPresenter> implements CityPickContract.View {
    private ListView mListView;
    private SideLetterBar mLetterBar;
    private CityListAdapter mCityAdapter;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initBundle() {


    }

    @Override
    protected int initLayout() {
        return R.layout.cp_activity_city_list;
    }

    protected void initView() {
        mListView = findViewById(R.id.listview_all_city);
        TextView overlay = findViewById(R.id.tv_letter_overlay);
        mLetterBar = findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });
        mCityAdapter = new CityListAdapter(this);
        mListView.setAdapter(mCityAdapter);
//        mCityAdapter.updateLocateState();
    }

    @Override
    protected void initTitle() {

    }


    @OnClick(R.id.back)
    public void onClick(View view) {
        finish();
    }

    protected void initData() {
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name, String citycode) {//选择城市
                Toast.makeText(CityPickerActivity.this, name, Toast.LENGTH_SHORT).show();
//                SharedPreUtils.getInstance().putString("city", name);
                Intent intent = new Intent(CityPickerActivity.this, EnterPriseMapActivity.class);
                intent.putExtra("city", name);
                intent.putExtra("citycode", citycode);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLocateClick() {//点击定位按钮
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);

            }
        });
        String sign = SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp());
        mPresenter.getCity(ApiService.appKey, SignUtils.temp(), sign, this);

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void getCity(String city) {
        HashSet<City> citys = new HashSet<>();
        CityPickerBean cityPickerBean = new Gson().fromJson(city, CityPickerBean.class);
        for (CityPickerBean.CityListBean bean : cityPickerBean.getCityList()
        ) {
            citys.add(new City(bean.getAdCode(), bean.getCity(), PinyinUtils.getPinYin(bean.getCity())));
        }

        //set转换list
        ArrayList<City> cities = new ArrayList<>(citys);
        //按照字母排序
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City city, City t1) {
                return city.getPinyin().compareTo(t1.getPinyin());
            }
        });
        mCityAdapter.setData(cities);
        mCityAdapter.updateLocateState(LocateState.SUCCESS, SPUtils.getInstance().getString("city", ""));
    }
}
