package com.xinzu.xiaodou.wxapi;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.ActivityCollector;
import com.radish.baselibrary.utils.AppUtil;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;
import com.xinzu.xiaodou.ZFBpay.utils.Orderutli;
import com.xinzu.xiaodou.ZFBpay.utils.SignUtils;
import com.xinzu.xiaodou.bean.SuccessOrderBean;
import com.xinzu.xiaodou.pro.MainActivity;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class AliPay {
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOfVfBTHeSf9UiSiy8mKVkeTafRJLY0c64b7tK+D4a64TuqdIUyzrYR8KD/vDnQHtF2njyyAtCuSXlWlJ98tTl+gz65udZ7a9FoLxaYAb2eOnCIDaCyKNeqlQkmg4Jr8n6NXQHQsIMUGLpoXsBHPIbZ0k02nCpyfVnSUV18V2Kg9AgMBAAECgYEAll62GJQ5VWLNwM2G+LYtuEc5ViWQ0hKMAgWI8L7NxxHsveglDScNyPzu7mkUKtZEeuqPxwHD2u8ZGNwCvJMaXVgbqZT/vB/+/fIbH6Mx82eXjYRncIVRk1h5SbIIZkciO2mCEystz3QxrEwo1Q5uTbt3Nome3wp/1EYmA5B6PlECQQD9m7FX/I/wnEw+wAdH/cdz0pmYg4AVBwxJORDHTwdIlXSTFJgOA22IVqluIZV6ZWxI0jlsKZ8h5yA9chIyz7J3AkEA6gU4dvGYhEgedtjZ3ttvbzQTDakqtOlXkOJ/PmUhO34P9RBxGqn1/SycrM1HpsnZ5x4HVkjJyRU95m6HQ9cT6wJAPWt3h9ejBKLV1FytZsIPi/11NXqjinQqhZFMfvikyu9nTDewm7Q40swqPIHlsb7eesRvI8Lwx0Uyia8WuTeUAQJBAKY/ES7GUJ/gj+vD/3qnDpHEP6jbvWAJyFA/5z8PHr2hv3pLtN3JUdwKPj8wEa+2A5uBJJsyWE8GTRQO/v69owMCQQD7gPdvTqIJeHur3TMO3XO5LlvLMOT76WbDAkcNRYwbA36EZ4LaKuGr7lPUeumve5UQ2QVEEo9bf1eZYymqwfUA";



    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAky1DZ8MPIUC10UcGSzIY9fElrpFO+X0b7jjme9psvRxCVs8Orzzcq82yPutaJH4E+VWwOFGwh8L7i00SWWBK4w+KOK00EQiJ0LCpFCLgj4EsIIeryGXPFWTgrqsN6gVkTCwMWbedxNJyr9rbBSa5wHYDZ9h1nJAqXzKFCte+92V1uTAOJV0Rntvoxi5Su7yhwSzoOuIfejbFr2wncMDfBErqCt1CTvslGlmv2JmDa21lWl1M9Y6jgVxT0CRGpnslW8ORAygre8ycIHghfVso236j32RinWKNdlOiGVrZHIA4GdCsu17mE127WZDv1vrF4b+GmThs4GdljcChAZkwcQIDAQAB";

    public static void pay(final Activity activity, final String OrderCode, final String Price, final OnAliPayCallBack callBack) {
        String orderInfo = Orderutli.getOrderInfo(OrderCode, Price);
        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
        AppUtil.checkAli(activity, new AppUtil.AppCheckCallback() {
            @Override
            public void callBack() {
                // 必须异步调用
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(activity);
                        final Map<String, String> resultMap = alipay.payV2(payInfo, true);
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
                                    IntentUtils.getInstance().with(activity, MainActivity.class).putInt("order", 1).start();
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