package com.xinzu.xiaodou.wxapi;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPay {
    private IWXAPI api;

    public WXPay(Context context, String appID,
                 String partnerID, String prepayId,
                 String packageValue, String nonceStr,
                 String timeStamp, String sign) {
        api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        api.registerApp(appID);
        pay(appID, partnerID, prepayId, packageValue, nonceStr, timeStamp, sign);
    }

    /*
     * 将订单信息传入支付第三方
     */
    private void pay(String appID, String partnerID,
                     String prepayId, String packageValue,
                     String nonceStr, String timeStamp,
                     String sign) {
//		//  本地签名(留着备用)
//		String s = "appid=" + appID +
//				"&noncestr=" + nonceStr +
//				"&package=" + packageValue +
//				"&partnerid=" + partnerID +
//				"&prepayid=" + prepayId +
//				"&timestamp=" + timeStamp +
//				"&key=" + "F379EAF3C831B04DE163469D1BEC345E";
//		s = MD5(s).toUpperCase();
//
//		sign  = s;
        if (!api.isWXAppInstalled()) {
            Log.d("weixinPay", "未安装微信");
            return;
        }
        if (!api.isWXAppSupportAPI()) {
            Log.d("weixinPay", "版本不支持");
            return;
        }

        PayReq request = new PayReq();
        request.appId = appID;
        request.partnerId = partnerID;
        request.prepayId = prepayId;
        request.packageValue = packageValue;
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        api.sendReq(request);
        System.out.println("============" + request.sign);

    }

}
