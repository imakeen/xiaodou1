package com.xinzu.xiaodou.http;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by hzy on 2019/1/10
 * ApiService
 *
 * @author Administrator
 */
public interface ApiService {
    public final static String appKey = "xzcxzfb";

    // 服务器url
    public final static String ServiceUrl = "http://39.105.178.240:8080/xinzuinterface/";


    public final static String image1 = "https://7869-xiaodou-1301502367.tcb.qcloud.la/banner-3.jpg?sign=ec3c10493095e35e19e41901c0010532&t=1583749209";
    public final static String image2 = "https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1591330828968&amp;di=6723477fd2eea596e8637b96d5fb0899&amp;imgtype=0&amp;src=http%3A%2F%2Fwww.ugainian.com%2Fyzp%2F20180516%2Fc8bb7751339119.58eab2d480830.jpg";


    public static String collectCityInfo = "collectCityInfo";

    @GET()
    Flowable<JsonObject> get(@Url String url);


    @POST
    Observable<ResponseBody> downLoadApk(@Url String url);


    @POST("collectCityInfo")
    @FormUrlEncoded
    Observable<ResponseBody> collectCityInfo(@Field("appKey") String appKey, @Field("timeStamp") String timeStamp, @Field("sign") String sign);

}
