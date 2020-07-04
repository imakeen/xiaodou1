package com.xinzu.xiaodou.pro.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
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
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.OnPermissionCallBack;
import com.xinzu.xiaodou.base.mvp.BaseMvpFragment;
import com.xinzu.xiaodou.bean.backTimeBean;
import com.xinzu.xiaodou.bean.getCarttypeBean;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.pro.activity.cartype.CarTypeAcitvity;
import com.xinzu.xiaodou.pro.activity.city.CityListenter;
import com.xinzu.xiaodou.pro.activity.city.CityPickerActivity;
import com.xinzu.xiaodou.pro.activity.registerlogin.RegisterActivity;
import com.xinzu.xiaodou.ui.activity.SearchActivity;
import com.xinzu.xiaodou.ui.activity.SelectmapActivity;
import com.xinzu.xiaodou.util.Day;
import com.xinzu.xiaodou.util.DialogUtil;
import com.xinzu.xiaodou.util.DownLoadApk;
import com.xinzu.xiaodou.util.SignUtils;
import com.xinzu.xiaodou.util.VersionUtils;
import com.xinzu.xiaodou.view.PickerDailog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * <pre>  *     author : radish  *     e-mail : 15703379121@163.com  *     time   : 2019/4/16  *     desc   :  * </pre>
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.Maps)
    TextView mMaps;
    @BindView(R.id.Mapse)
    TextView Mapse;

    @BindView(R.id.Ri_Qu)
    TextView tv_pick_day;
    @BindView(R.id.Tian_Shu)
    TextView tianshu;
    @BindView(R.id.Ri_qi)
    TextView tv_return_day;
    @BindView(R.id.Zhou_ri)
    TextView tv_pick_week;
    @BindView(R.id.Zhao_ris)
    TextView tv_return_week;
    @BindView(R.id.Lease_immediately)
    Button mLease_immediately;
    @BindView(R.id.mapty)
    MapView mMapView1;
    @BindView(R.id.Imge)
    ConvenientBanner convenientBanner;//轮播图组件
    PickerDailog pickerDailog;

    private String title = "";


    private AMap aMap;
    private boolean isShowPermission = true;
    private String citys;
    private String Pickuplatitude;
    private String Pickuplongitude;
    private String Citycode;

    private String names;
    AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    private GeocodeSearch mGeocoderSearch;

    int context;
    private String aoiName;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static HomeFragment newInstance(String title) {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        return R.layout.fragment_homes;
    }


    @Override
    protected void initView() {
        getPermission(new OnPermissionCallBack() {
            @Override
            public void permissionPass(String[] permissions) {

            }

            @Override
            public void permissionRefuse(String[] permissions) {
            }
        }, permissions);
        if (aMap == null) {
            aMap = mMapView1.getMap();
        }
        myLocati();
        // banner();
        tv_pick_day.setText(Day.pickcar_date("day", true));
        tv_pick_week.setText(Day.pickcar_date("week", true));
        tv_return_day.setText(Day.pickcar_date("day", false));
        tv_return_week.setText(Day.pickcar_date("week", false));
    }

    //检测版本
    private void update() {
        Map<String, Integer> map = new HashMap<>();
        map.put("version", VersionUtils.versionCode(getActivity()));
        mPresenter.update(new Gson().toJson(map));
    }

    @Override
    public void update(String body) {
        LogUtils.e(body);
        try {
            JSONObject jsonObject = new JSONObject(body);
            if (jsonObject.getString("code").equals("1")) {
                ToastUtils.showShort(jsonObject.getString("message"));
                String url = jsonObject.getString("url");
                DownLoadApk downLoadApk = new DownLoadApk(getActivity(), url);
                DialogUtil.showAlterDialog(getActivity(), "检测到有新版本，马上更新", (dialog, view1) -> {
                    SPUtils.getInstance().clear();
                    downLoadApk.downLoadApk();
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadData() {
        update();
    }

    @Override
    protected void initListener() {
        CityListenter cityListenter = new CityListenter();
        cityListenter.setonListener(new CityListenter.onListener() {
            @Override
            public void select(String city_title, String city, String citycode, String pickuplongitude, String pickuplatitude) {
                Mapse.setText(city_title);
                mMaps.setText(city);
                if (citycode != null) {
                    Citycode = citycode;
                }
                if (pickuplatitude != null) {
                    Pickuplongitude = pickuplongitude;
                }
                if (pickuplatitude != null) {
                    Pickuplatitude = pickuplatitude;
                }

            }
        });
    }

    @OnClick({R.id.Lease_immediately, R.id.Mapse, R.id.Ri_Qu,
            R.id.ll_qu, R.id.ll_huan, R.id.Maps})
    public void onClieck(View view) {
        switch (view.getId()) {
            case R.id.Maps:
                Intent intent = new Intent(getContext(), CityPickerActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("bundle", bundle);
                bundle.putString("city", mMaps.getText().toString());
                bundle.putString("Citycode", Citycode);
                ActivityUtils.startActivity(intent);
                break;
            case R.id.ll_qu:
                startPickDailog(true);
                break;
            case R.id.ll_huan:
                startPickDailog(false);
                break;
            case R.id.Mapse:
                if (!"请选择".equals(Mapse.getText().toString())) {
                    Intent intent1 = new Intent(getContext(), SelectmapActivity.class);
                    intent1.putExtra("city", mMaps.getText().toString());
                    intent1.putExtra("city_title", Mapse.getText().toString());
                    startActivity(intent1);
                }
                break;

            case R.id.Lease_immediately:
                if ("请选择".equals(mMaps.getText().toString())) {
                    ToastUtil.showShort("请选择城市");
                    return;
                }
                if ("请选择".equals(Mapse.getText().toString())) {
                    ToastUtil.showShort("请选择取还地点");
                    return;
                }
                getCattype();


                break;
        }
    }

    private void getCattype() {
        getCarttypeBean bean = new getCarttypeBean();
        bean.setAppKey(ApiService.appKey);
        //bean.setCarGroupId(0);
        bean.setOrderChannel(4);
        bean.setChannelId(1);
        String pickminute = tv_pick_week.getText().toString();
        bean.setPickupDate(tv_pick_day.getText().toString() + " " + pickminute.substring(pickminute.length() - 5));
        bean.setPickuplongitude(Pickuplongitude);
        bean.setPickuplatitude(Pickuplatitude);
        String returnminute = tv_return_week.getText().toString();
        bean.setReturnDate(tv_return_day.getText().toString() + " " + returnminute.substring(returnminute.length() - 5));
        bean.setReturnlongitude(Pickuplongitude);
        bean.setReturnlatitude(Pickuplatitude);
        bean.setTimeStamp(SignUtils.temp());
        bean.setSign(SignUtils.encodeSign("xzcxzfb" + "112233", SignUtils.temp()));
        getCarttypeBean.StoreListBean storeListBean = new getCarttypeBean.StoreListBean();
        storeListBean.setPickupCityCode(Citycode);
        storeListBean.setReturnCityCode(Citycode);
        ArrayList<getCarttypeBean.StoreListBean> arrayList = new ArrayList<>();
        arrayList.add(storeListBean);
        bean.setStoreList(arrayList);
        Intent intent = new Intent(getActivity(), CarTypeAcitvity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cartype", bean);
        bundle.putString("city", mMaps.getText().toString());
        bundle.putString("cityinfo", Mapse.getText().toString());
        bundle.putString("day", tianshu.getText().toString());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    /**
     * 点击启动时间选择器
     */
    private void startPickDailog(Boolean qu_or_back) {
        backTimeBean timeBean = new backTimeBean();

        timeBean.setQu_day(tv_pick_day.getText().toString());
        timeBean.setQu_week_time(tv_pick_week.getText().toString());

        timeBean.setBack_day(tv_return_day.getText().toString());
        timeBean.setBack_week_time(tv_return_week.getText().toString());

        pickerDailog = new PickerDailog(qu_or_back, Objects.requireNonNull(getContext()), timeBean, getActivity());
        if (!pickerDailog.isShowing()) {
            pickerDailog.show();
        }
        pickerDailog.setOnDateSelectFinished(new PickerDailog.callback() {
            @Override
            public void getTime(String startTime_day, String startTime_week, String endTime_day, String endTime_week
                    , String day) {
                tv_pick_day.setText(startTime_day);
                tv_pick_week.setText(startTime_week);
                tv_return_day.setText(endTime_day);
                tv_return_week.setText(endTime_week);
                tianshu.setText(day);
            }
        });
    }

    /**
     * 轮播图
     */
/*    public void banner() {
        List<String> list;
        String[] images = new String[2];
        images[0] = ApiService.image1;
        images[1] = ApiService.image2;
        list = Arrays.asList(images);
        convenientBanner.setPointViewVisible(true);
        //允许手动轮播
        convenientBanner.setManualPageable(true);
        //设置自动轮播的时间
        convenientBanner.startTurning(3000);
        //设置点击事件
        //泛型为具体实现类ImageLoaderHolder
        convenientBanner.setPages(new CBViewHolderCreator<NetImageLoadHolder>() {
            @Override
            public NetImageLoadHolder createHolder() {
                return new NetImageLoadHolder();
            }
        }, list);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }*/


 /*   public class NetImageLoadHolder implements Holder<String> {
        private ImageView image_lv;

        //可以是一个布局也可以是一个Imageview
        @Override
        public ImageView createView(Context context) {
            image_lv = new ImageView(context);
            image_lv.setScaleType(ImageView.ScaleType.FIT_XY);
            return image_lv;
        }
        @Override
        public void UpdateUI(Context context, int position, String data) {
            //Glide框架
            Glide.with(context.getApplicationContext()).load(data).into(image_lv);
        }

    }*/
    private void myLocati() {

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        mGeocoderSearch = new GeocodeSearch(getContext());
        // checkPermissions();
        mGeocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {
            }

            @Override
            public void onRegeocodeSearched(final RegeocodeResult result, int rCode) {
                if (rCode == 1000) {
                    aoiName = result.getRegeocodeAddress().getAois().get(context).getAoiName();
                    citys = result.getRegeocodeAddress().getCity();
                    mMaps.setText(citys);
                    Mapse.setText(aoiName);
                    Citycode = result.getRegeocodeAddress().getAdCode();
                    String code = Citycode.substring(0, 4);
                    Citycode = code + "00";
                    Pickuplongitude = result.getRegeocodeQuery().getPoint().getLongitude() + "";
                    Pickuplatitude = result.getRegeocodeQuery().getPoint().getLatitude() + "";
                }
            }
        });

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
//          经纬度转地址 高德地图
                    LatLonPoint lp = new LatLonPoint(latitude, longitude);
                    RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
                    mGeocoderSearch.getFromLocationAsyn(query);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
