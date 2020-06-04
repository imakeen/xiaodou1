package com.xinzu.xiaodou.wxapi;

/**
 * author : radish
 * e-mail : 15703379121@163.com
 * time   : 2019/09/12
 * desc   : xxxx 描述
 */
public class WxEvent {
    private int type;
    private String headimgurl;
    private String openid;
    private String nickname;

    public WxEvent(int type) {
        this.type = type;
    }

    public WxEvent(String openid, String nickname, String headimgurl) {
        this.openid = openid;
        this.nickname = nickname;
        this.headimgurl = headimgurl;
    }

    public int getType() {
        return type;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }
}
