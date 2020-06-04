package com.xinzu.xiaodou.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.radish.baselibrary.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.xinzu.xiaodou.util.CommonUtil;

import org.greenrobot.eventbus.EventBus;


/******************************************
 * 类描述： 微信支付结果处理类 类名称：WXPayEntryActivity
 ******************************************/
public final class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mIWXAPI;
    private static final String TAG = "微信支付回调";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIWXAPI = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, CommonUtil.WECHAT_APPID, false);
        try {
            mIWXAPI.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) { // 支付成功
                ToastUtil.showShort("支付成功");
                WxEvent messageEvent = new WxEvent(1);
                EventBus.getDefault().post(messageEvent);
            } else if (resp.errCode == -2) { // 用户取消支付
                ToastUtil.showShort("取消支付");
//                WxEvent messageEvent = new WxEvent(2);
//                EventBus.getDefault().post(messageEvent);
            } else {
                ToastUtil.showShort("支付失败");
//                WxEvent messageEvent = new WxEvent(-1);
//                EventBus.getDefault().post(messageEvent);
            }
            finish();
        }
    }

}
