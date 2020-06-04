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
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.pro.activity.getcity.CityPickerActivity;
import com.xinzu.xiaodou.pro.activity.getcity.EnterPriseMapActivity;
import com.xinzu.xiaodou.pro.fragment.home.utils.Day;
import com.xinzu.xiaodou.pro.fragment.home.utils.PickerDailog;
import com.xinzu.xiaodou.bean.backTimeBean;
import com.xinzu.xiaodou.util.SharedPreUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
    TextView mRi_qu;
    @BindView(R.id.Tian_Shu)
    TextView tianshu;
    @BindView(R.id.Ri_qi)
    TextView ri_qi;
    @BindView(R.id.Zhou_ri)
    TextView zhou_ri;
    @BindView(R.id.Zhao_ris)
    TextView zhao_ris;
    @BindView(R.id.Lease_immediately)
    Button mLease_immediately;
    @BindView(R.id.mapty)
    MapView mMapView1;
    @BindView(R.id.Imge)
    ConvenientBanner convenientBanner;//轮播图组件
    PickerDailog pickerDailog;
    private static final int PERMISSON_REQUESTCODE = 0;
    private String title = "";
    private PermissionListener mListener;
    public static final int PERMISSION_REQUESTCODE = 0x001;
    private AMap aMap;
    private boolean isShowPermission = true;
    private backTimeBean mBackInfo;
    private String names;
    private String lat;
    private String lng;
    private String nn;
    private String tumesy;
    private String subsytem;
    private String tiemsg;

    private GeocodeSearch mGeocoderSearch;
    private String formatAddress;
    int context;

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
        if (aMap == null) {
            aMap = mMapView1.getMap();
            if (!isShowPermission) {
                Log.e("", "无权限  initMap: ");
            } else {
                permission();
            }
        }
        myLocati();
        banner();
    }

    @Override
    protected void loadData() {
        names = SharedPreUtils.getInstance().getString("names", "");
        lat = SharedPreUtils.getInstance().getString("lat", "");
        lng = SharedPreUtils.getInstance().getString("lng", "");
        nn = SharedPreUtils.getInstance().getString("nn", "");
        Log.e("asdlkjhlk", names);


        mRi_qu.setText(Day.pickcar_date("day", true));
        zhou_ri.setText(Day.pickcar_date("week", true));
        ri_qi.setText(Day.pickcar_date("day", false));
        zhao_ris.setText(Day.pickcar_date("week", false));

    }


    @Override
    protected void initListener() {

    }


    @OnClick({R.id.Lease_immediately, R.id.Mapse, R.id.Ri_Qu,
            R.id.ll_qu, R.id.Maps})
    public void onClieck(View view) {
        switch (view.getId()) {
            case R.id.Maps:
                ActivityUtils.startActivity(CityPickerActivity.class);
                break;
            case R.id.Ri_Qu:
                //取车时间
            case R.id.ll_qu:
                //还车时间
                startPickDailog();
                break;
            case R.id.Mapse:
                if (!mMaps.equals("请选择")) {
                    Intent intent = new Intent(getContext(), EnterPriseMapActivity.class);
                    intent.putExtra("city", mMaps.getText().toString());
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 点击启动时间选择器
     */
    private void startPickDailog() {
        backTimeBean timeBean = new backTimeBean();

        timeBean.setQu_day(mRi_qu.getText().toString());
        timeBean.setQu_week_time(zhou_ri.getText().toString());

        timeBean.setBack_day(ri_qi.getText().toString());
        timeBean.setBack_week_time(zhao_ris.getText().toString());

        pickerDailog = new PickerDailog(getContext(), timeBean, getActivity());
        if (!pickerDailog.isShowing()) {
            pickerDailog.show();
        }
        pickerDailog.setOnDateSelectFinished(new PickerDailog.callback() {
            @Override
            public void getTime(String startTime_day, String startTime_week, String endTime_day, String endTime_week) {
                mRi_qu.setText(startTime_day);
            }
        });
    }

    /**
     * 轮播图
     */
    public void banner() {
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
    }


    @Override
    public void updateUI(String body) {

    }

    public class NetImageLoadHolder implements Holder<String> {
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
            Glide.with(context).load(data).into(image_lv);
        }

    }

    private void myLocati() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        mGeocoderSearch = new GeocodeSearch(getContext());
        checkPermissions();
        mGeocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            private String adCode;
            private String citys;

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {

            }

            @Override
            public void onRegeocodeSearched(final RegeocodeResult result, int rCode) {
                if (rCode == 1000) {
                    String aoiName = result.getRegeocodeAddress().getAois().get(context).getAoiName();
                    citys = result.getRegeocodeAddress().getCity();
                    mMaps.setText(citys);
                    Mapse.setText(aoiName);
                    if (names.equals("")) {
                        Mapse.setText(aoiName);
                    } else {
                        Mapse.setText(names);
                    }
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

    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(getActivity(),
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(), perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;

    }

    private void permission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        requestRunPermisssion(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
//                Log.e(TAG, " 权限  setUpMap: " );
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                if (isShowPermission) {

                    isShowPermission = false;
                    for (int i = 0; i < deniedPermission.size(); i++) {
                        //  Log.e(TAG, " 无 权限  : "+deniedPermission.get(i) );
                    }

                }
            }
        });
    }

    public void requestRunPermisssion(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions
                    (getActivity(), permissionLists.toArray
                            (new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SharedPreUtils.getInstance().getString("city", "").isEmpty()) {
            mMaps.setText(SharedPreUtils.getInstance().getString("city", ""));
            Mapse.setText("请选择");
        }
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0) {
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        //说明都授权了
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }
}
