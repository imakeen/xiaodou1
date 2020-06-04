package com.xinzu.xiaodou.http;

import android.app.Activity;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.radish.baselibrary.utils.ActivityCollector;
import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.pro.MainActivity;
import com.xinzu.xiaodou.util.CommentUtil;
import com.xinzu.xiaodou.util.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public abstract class SuccessfulConsumer implements Consumer<ResponseBody> {
    private String url = "";

    public SuccessfulConsumer() {
    }

    public SuccessfulConsumer(String url) {
        this.url = url;
    }

    public static void LogShowMap(Map<String, String> map) {
        StringBuilder mapStr = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            mapStr.append("Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n");
        }
        LogUtils.e(mapStr.toString());
    }

    @Override
    public void accept(ResponseBody responseBody) throws Exception {
        String json = responseBody.string();
        LogUtils.e("获取网络数据:  " + url + "   " + json);
        JSONObject jsonObject = new JSONObject(json);
        String code = jsonObject.getString("code");
        if ("0".equals(code)) {
            success(json);
        } else if ("402".equals(code) || "401".equals(code)) {
            Activity activity = ActivityCollector.getActivity(MainActivity.class);
            if (activity != null && activity instanceof MainActivity) {
                ((MainActivity) activity).startLogin();
            }
            CommentUtil.startLogin();
        } else {
            LogUtils.e("code:" + code);
            showMessage(json);
        }


    }

    public static void showMessage(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String msg = jsonObject.getString("msg");
            if (!TextUtils.isEmpty(msg)) {
                ToastUtils.showShort(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getKey(String json, String key) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = CommonUtil.getStr(jsonObject.getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isCodeRight(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String code = jsonObject.getString("code");
            if ("0".equals(code)) {
                return true;
            } else {
                showMessage(json);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public abstract void success(String jsonObject);

}
