package com.xinzu.xiaodou.wxapi;


import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.util.CommonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    private String headimgurl;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.e("WXPayEntryActivity");
        api = MyApp.api;
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
        LogUtils.e("Entry    = == = =   ========= == = = = ");
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.e("微信登录transaction" + resp);
        if (resp instanceof SendAuth.Resp) {
            wxLogin(resp);
        } else {
            wechatShare(resp);
            this.finish();
        }
    }

    private void wxLogin(BaseResp resp) {

        wechatLogin(resp, (json, openid, access_token) -> {
            try {
                LogUtils.e("json:" + json);
                SPUtils.getInstance().put("wxinfo", json);
                JSONObject object = new JSONObject(json);
                nickname = object.getString("nickname");
                int sex = Integer.parseInt(object.get("sex").toString());
                headimgurl = object.getString("headimgurl");
                EventBus.getDefault().postSticky(new WxEvent(openid, nickname, headimgurl));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    public interface WxUserInfo {
        void userInfo(String json, String openid, String access_token);
    }


    /**
     * 微信分享
     *
     * @param resp
     */
    private void wechatShare(BaseResp resp) {
    }

    /**
     * 微信登录
     *
     * @param resp
     */
    private void wechatLogin(BaseResp resp, final WxUserInfo info) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp newResp = (SendAuth.Resp) resp;
                //获取微信传回的code
                String code = newResp.code;
                LogUtils.e("返回的CODE：  " + code);
                String path = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=" + CommonUtil.WECHAT_APPID
                        + "&secret=" + CommonUtil.WECHAT_APPSECRET
                        + "&Code=" + code
                        + "&grant_type=authorization_code";
                LogUtils.e("微信登录提交信息：" + path);
                runOnUiThread(() -> MyApp.apiService(ApiService.class).get(path.trim())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(jsonObject -> {
                            String openid = jsonObject.get("openid").getAsString();
                            String unionid = jsonObject.get("unionid").getAsString();
                            String access_token = jsonObject.get("access_token").getAsString();
                            LogUtils.e("获得的 openid：  " + openid + " unionid " + unionid);
                            getUserMesg(access_token, openid, info);

                            finish();
                        }, throwable -> {
                            LogUtils.e("异常：" + throwable.toString());
                        }));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showShort("登录取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.showShort("登录失败");
                finish();
                break;

        }
    }


    private void getUserMesg(final String access_token, final String openid, final WxUserInfo info) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        LogUtils.e("微信登录提交信息：" + path);

        runOnUiThread(() -> MyApp.apiService(ApiService.class).get(path.trim())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
//                    String json = jsonObject.string();
                    LogUtils.e("jsonObject:" + jsonObject);
                    if (info != null) {
                        info.userInfo(jsonObject.toString(), openid, access_token);
                    }
                }, throwable -> {
                    LogUtils.e("异常：" + throwable.toString());
                }));
    }


}
