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
    @Override
    public void accept(ResponseBody responseBody) throws Exception {
        String json = responseBody.string();
        JSONObject jsonObject = new JSONObject(json);
        success(json);
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

    public abstract void success(String jsonObject);

}
