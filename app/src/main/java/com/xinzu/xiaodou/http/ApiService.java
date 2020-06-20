package com.xinzu.xiaodou.http;

import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by hzy on 2019/1/10
 * ApiService
 *
 * @author Administrator
 */
public interface ApiService {
    String appKey = "xzcxzfb";

    // 服务器url
    String ServiceUrl = "http://39.105.178.240:8080/xinzuinterface/";
    //String ServiceUrl = "http://192.168.0.141:9080/";
    String image1 = "https://7869-xiaodou-1301502367.tcb.qcloud.la/banner-3.jpg?sign=ec3c10493095e35e19e41901c0010532&t=1583749209";
    String image2 = "https://7869-xiaodou-1301502367.tcb.qcloud.la/banner-2.jpg?sign=db2c68808d293088dff25e175eb4200a&t=1583749228";
    //城市列表
    //String collectCityInfo = "collectCityInfo";
    //左边车型
    //String getCarGroups = "getCarGroups";
    // String searchVehicle = "searchVehicle";
    //获取验证码
    //String getMsgCode = "getMsgCode";
    //登录注册
   // String loging = "userLoginApp";
    //登录注册
    @POST("userLoginApp")
    Observable<ResponseBody> userLoginApp(@Body RequestBody body);
    //获取验证码
    @POST("getMsgCode")
    Observable<ResponseBody> getMsgCode(@Body RequestBody body);

    //城市列表
    @POST("collectCityInfo")
    Observable<ResponseBody> collectCityInfo(@Body RequestBody body);

    //左边车型
    @POST("getCarGroups")
    Observable<ResponseBody> getCarGroups(@Body RequestBody body);

    //该车型的车辆
    @POST("searchVehicle")
    Observable<ResponseBody> searchVehicle(@Body RequestBody body);


    //意见反馈
    String saveFeedback = "saveFeedback";

    @POST("saveFeedback")
    Observable<ResponseBody> saveFeedback(@Body RequestBody body);

    //获取驾驶员信息
    @POST("getConsumers")
    Observable<ResponseBody> getConsumers(@Body RequestBody body);

    //修改增加驾驶员
    @POST("editConsumers")
    Observable<ResponseBody> editConsumers(@Body RequestBody body);

    //获取证件类型
    @POST("getCardType")
    Observable<ResponseBody> getCardType(@Body RequestBody body);

    //删除驾驶员信息
    @POST("deleteConsumers")
    Observable<ResponseBody> deleteConsumers(@Body RequestBody body);


    //创建订单
    @POST("createOrder")
    Observable<ResponseBody> createOrder(@Body RequestBody body);


    //支付宝支付接口
    @POST("AppPay")
    Observable<ResponseBody> AppPay(@Body RequestBody body);


    //查看订单
    @POST("userOrderList")
    Observable<ResponseBody> userOrderList(@Body RequestBody body);

    @GET()
    Flowable<JsonObject> get(@Url String url);


    //订单详情
    @POST("orderDetail")
    Observable<ResponseBody> orderDetail(@Body RequestBody body);

    //费用详情
    @POST("getOrPriceDetail")
    Observable<ResponseBody> getOrPriceDetail(@Body RequestBody body);

    //删除订单
    @POST("deleteOrder")
    Observable<ResponseBody> deleteOrder(@Body RequestBody body);


    //取消订单 查看是否需要退费
    @POST("cancleOrders")
    Observable<ResponseBody> cancleOrders(@Body RequestBody body);


    //取消订单 更改状态
    @POST("cancleOrder")
    Observable<ResponseBody> cancleOrder(@Body RequestBody body);
    //支付宝退单


    @POST("refundApp")
    Observable<ResponseBody> refundApp(@Body RequestBody body);
}
