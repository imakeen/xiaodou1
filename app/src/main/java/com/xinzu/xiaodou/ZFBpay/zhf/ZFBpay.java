package com.xinzu.xiaodou.ZFBpay.zhf;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.xinzu.xiaodou.ZFBpay.utils.Orderutli;
import com.xinzu.xiaodou.ZFBpay.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ZFBpay {

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOfVfBTHeSf9UiSiy8mKVkeTafRJLY0c64b7tK+D4a64TuqdIUyzrYR8KD/vDnQHtF2njyyAtCuSXlWlJ98tTl+gz65udZ7a9FoLxaYAb2eOnCIDaCyKNeqlQkmg4Jr8n6NXQHQsIMUGLpoXsBHPIbZ0k02nCpyfVnSUV18V2Kg9AgMBAAECgYEAll62GJQ5VWLNwM2G+LYtuEc5ViWQ0hKMAgWI8L7NxxHsveglDScNyPzu7mkUKtZEeuqPxwHD2u8ZGNwCvJMaXVgbqZT/vB/+/fIbH6Mx82eXjYRncIVRk1h5SbIIZkciO2mCEystz3QxrEwo1Q5uTbt3Nome3wp/1EYmA5B6PlECQQD9m7FX/I/wnEw+wAdH/cdz0pmYg4AVBwxJORDHTwdIlXSTFJgOA22IVqluIZV6ZWxI0jlsKZ8h5yA9chIyz7J3AkEA6gU4dvGYhEgedtjZ3ttvbzQTDakqtOlXkOJ/PmUhO34P9RBxGqn1/SycrM1HpsnZ5x4HVkjJyRU95m6HQ9cT6wJAPWt3h9ejBKLV1FytZsIPi/11NXqjinQqhZFMfvikyu9nTDewm7Q40swqPIHlsb7eesRvI8Lwx0Uyia8WuTeUAQJBAKY/ES7GUJ/gj+vD/3qnDpHEP6jbvWAJyFA/5z8PHr2hv3pLtN3JUdwKPj8wEa+2A5uBJJsyWE8GTRQO/v69owMCQQD7gPdvTqIJeHur3TMO3XO5LlvLMOT76WbDAkcNRYwbA36EZ4LaKuGr7lPUeumve5UQ2QVEEo9bf1eZYymqwfUA";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAky1DZ8MPIUC10UcGSzIY9fElrpFO+X0b7jjme9psvRxCVs8Orzzcq82yPutaJH4E+VWwOFGwh8L7i00SWWBK4w+KOK00EQiJ0LCpFCLgj4EsIIeryGXPFWTgrqsN6gVkTCwMWbedxNJyr9rbBSa5wHYDZ9h1nJAqXzKFCte+92V1uTAOJV0Rntvoxi5Su7yhwSzoOuIfejbFr2wncMDfBErqCt1CTvslGlmv2JmDa21lWl1M9Y6jgVxT0CRGpnslW8ORAygre8ycIHghfVso236j32RinWKNdlOiGVrZHIA4GdCsu17mE127WZDv1vrF4b+GmThs4GdljcChAZkwcQIDAQAB";

    public void pays(String totlprice, String TT , final Activity activity, final Handler handler){
        String orderInfo = Orderutli.getOrderInfo("小豆租车", "该测试商品的详细描述", totlprice,TT);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = new Message();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
