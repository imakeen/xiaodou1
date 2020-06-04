package com.xinzu.xiaodou.ZFBpay;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.radish.baselibrary.base.BaseActivity;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.ZFBpay.utils.PayResult;
import com.xinzu.xiaodou.ZFBpay.zhf.ZFBpay;

import java.util.Map;

public class pay extends BaseActivity {
    private TextView myte;
    private ZFBpay mZfbpay;
    private String payAmount2;
    private String orderCode;
    private TextView payst1;
    private String orderId;
    private String actualAmount;
    //支付宝支付结果
    /*********************************************/
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /** 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为结束的通知。 */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus(); // 判断resultStatus 为9000则代表支付成功

            Log.d("reuslt", resultInfo);
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
                Toast.makeText(pay.this, "支付成功", Toast.LENGTH_SHORT).show();
                Log.e("支付", "成功");
            } else {
                // 判断resultStatus 为非"9000"则代表可能支付失败
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    Toast.makeText(pay.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                    Log.e("支付", "支付结果确认中");
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    Toast.makeText(pay.this, "支付失败", Toast.LENGTH_SHORT).show();
                    Log.e("支付", "支付失败");
                }
            }
        }
    };
    @Override
    protected void initData() {
        Intent intent = getIntent();
        //
        orderCode = intent.getStringExtra("orderCode");
        payAmount2 = intent.getStringExtra("payAmount2");
        Intent intent1 = getIntent();
        orderId = intent1.getStringExtra("orderId");
        actualAmount = intent1.getStringExtra("actualAmount");
        mZfbpay = new ZFBpay();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initBundle() {
    }

    @Override
    protected int initLayout() {
        return R.layout.pay;
    }

    @Override
    protected void initView() {
        myte = findViewById(R.id.myte);
        payst1 = findViewById(R.id.Payst1);
        myte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZfbpay.pays(payAmount2, orderCode, pay.this, mHandler);
            }
        });
        payst1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZfbpay.pays(actualAmount, orderId, pay.this, mHandler);
            }
        });
    }

    @Override
    protected void initTitle() {

    }


}
