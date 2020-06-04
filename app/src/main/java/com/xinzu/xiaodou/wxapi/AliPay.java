package com.xinzu.xiaodou.wxapi;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.radish.baselibrary.Intent.IntentUtils;
import com.radish.baselibrary.utils.AppUtil;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.ToastUtil;


import java.util.Map;


public class AliPay {
    public static void pay(final Activity activity, final String sign, final OnAliPayCallBack callBack) {
        AppUtil.checkAli(activity, new AppUtil.AppCheckCallback() {
            @Override
            public void callBack() {
                // 必须异步调用
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(activity);
                        final Map<String, String> resultMap = alipay.payV2(sign, true);
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
                                   // IntentUtils.getInstance().with(activity, My_dingdan.class).putInt("index",1).start();
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