package com.xinzu.xiaodou.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.ActivityCollector;
import com.radish.baselibrary.utils.AppUtil;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.ZFBpay.utils.OrderInfoUtil2_0;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.ui.activity.CarInfoActivity;

import java.util.Map;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;


public class AliPay {
    // public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOfVfBTHeSf9UiSiy8mKVkeTafRJLY0c64b7tK+D4a64TuqdIUyzrYR8KD/vDnQHtF2njyyAtCuSXlWlJ98tTl+gz65udZ7a9FoLxaYAb2eOnCIDaCyKNeqlQkmg4Jr8n6NXQHQsIMUGLpoXsBHPIbZ0k02nCpyfVnSUV18V2Kg9AgMBAAECgYEAll62GJQ5VWLNwM2G+LYtuEc5ViWQ0hKMAgWI8L7NxxHsveglDScNyPzu7mkUKtZEeuqPxwHD2u8ZGNwCvJMaXVgbqZT/vB/+/fIbH6Mx82eXjYRncIVRk1h5SbIIZkciO2mCEystz3QxrEwo1Q5uTbt3Nome3wp/1EYmA5B6PlECQQD9m7FX/I/wnEw+wAdH/cdz0pmYg4AVBwxJORDHTwdIlXSTFJgOA22IVqluIZV6ZWxI0jlsKZ8h5yA9chIyz7J3AkEA6gU4dvGYhEgedtjZ3ttvbzQTDakqtOlXkOJ/PmUhO34P9RBxGqn1/SycrM1HpsnZ5x4HVkjJyRU95m6HQ9cT6wJAPWt3h9ejBKLV1FytZsIPi/11NXqjinQqhZFMfvikyu9nTDewm7Q40swqPIHlsb7eesRvI8Lwx0Uyia8WuTeUAQJBAKY/ES7GUJ/gj+vD/3qnDpHEP6jbvWAJyFA/5z8PHr2hv3pLtN3JUdwKPj8wEa+2A5uBJJsyWE8GTRQO/v69owMCQQD7gPdvTqIJeHur3TMO3XO5LlvLMOT76WbDAkcNRYwbA36EZ4LaKuGr7lPUeumve5UQ2QVEEo9bf1eZYymqwfUA";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "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";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAky1DZ8MPIUC10UcGSzIY9fElrpFO+X0b7jjme9psvRxCVs8Orzzcq82yPutaJH4E+VWwOFGwh8L7i00SWWBK4w+KOK00EQiJ0LCpFCLgj4EsIIeryGXPFWTgrqsN6gVkTCwMWbedxNJyr9rbBSa5wHYDZ9h1nJAqXzKFCte+92V1uTAOJV0Rntvoxi5Su7yhwSzoOuIfejbFr2wncMDfBErqCt1CTvslGlmv2JmDa21lWl1M9Y6jgVxT0CRGpnslW8ORAygre8ycIHghfVso236j32RinWKNdlOiGVrZHIA4GdCsu17mE127WZDv1vrF4b+GmThs4GdljcChAZkwcQIDAQAB";
    public static final String PARTNER = "2021001168653446";

    public static void pay(final Activity activity, final String OrderCode, final String Price, final OnAliPayCallBack callBack) {

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PARTNER, OrderCode, Price, true);
        //将map解析成一个String类型的支付订单
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, true);
        //最终，结合orderparam参数与sign签名字串，搞成orderInfo字串；
        final String orderInfo = orderParam + "&" + sign;

//        String orderInfo = Orderutli.getOrderInfo(OrderCode, Price);
//        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
//        try {
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA2\"";
        AppUtil.checkAli(activity, new AppUtil.AppCheckCallback() {
            @Override
            public void callBack() {
                // 必须异步调用
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(activity);
                        final Map<String, String> resultMap = alipay.payV2(orderInfo, true);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PayResult result = new PayResult(resultMap);
                                String resultStatus = result.getResultStatus();
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    LogUtils.e("handleMessage: 已经支付成功，支付成功");
                                    ToastUtil.showShort("支付成功");
                                    if (callBack != null)
                                        callBack.paySuccess();
                                } else if (TextUtils.equals(resultStatus, "6001")) {
                                    LogUtils.e("handleMessage: 已经取消支付");
                                    ToastUtil.showShort("支付取消");
                                    ActivityCollector.finishAll();
                                    Intent intent = new Intent(activity, MainActivity.class);
                                    intent.putExtra("order", 1);
                                    startActivity(intent);
                                } else {
                                    LogUtils.e("handleMessage: 未知状态");
                                    ToastUtil.showShort(resultStatus);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

    }

    public interface OnAliPayCallBack {
        void paySuccess();
    }

    public static class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }

            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
    }

}