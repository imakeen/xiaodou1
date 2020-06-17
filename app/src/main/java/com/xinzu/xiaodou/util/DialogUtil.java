package com.xinzu.xiaodou.util;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.radish.baselibrary.dialog.OnDialogViewClickListener;
import com.radish.baselibrary.dialog.RadishDialog;
import com.radish.baselibrary.utils.LogUtils;
import com.radish.baselibrary.utils.SharedPreferencesHelper;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.base.OnPermissionCallBack;
import com.xinzu.xiaodou.http.ApiService;
import com.xinzu.xiaodou.http.RxSchedulers;
import com.xinzu.xiaodou.http.SuccessfulConsumer;
import com.xinzu.xiaodou.wxapi.AliPay;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : radish
 * e-mail : 15703379121@163.com
 * time   : 2019/07/10
 * desc   : xxxx 描述
 */
public class DialogUtil {

    public static void showCallDialog(final BaseGActivity activity, String number) {
        showAlterDialog(activity, "拨打电话：" + number, new OnAlterDialogCallBack() {
            @Override
            public void callBack(RadishDialog dialog, View view) {
                activity.getPermission(new OnPermissionCallBack() {
                    @Override
                    public void permissionPass(String[] permissions) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + CommonUtil.getStr(number)));
                        activity.startActivity(intent);
                    }

                    @Override
                    public void permissionRefuse(String[] permissions) {

                    }
                }, Manifest.permission.CALL_PHONE);
            }
        });
    }

    public static void showDeleteDialog(Context context, final OnAlterDialogCallBack callBack) {
        showAlterDialog(context, "确定删除吗？", "取消", "确定", callBack);
    }


    public static void showAlterDialog(Context context, String msg, final OnAlterDialogCallBack callBack) {
        showAlterDialog(context, msg, "取消", "确定", callBack);
    }

    public static void kefu(Context context, String msg, final OnAlterDialogCallBack callBack) {
        showAlterDialog(context, msg, "呼叫", "取消", callBack);
    }

    public static void showAlterDialog(Context context, String msg, String cancelText, String submitText, final OnAlterDialogCallBack callBack) {
        new RadishDialog.Builder(context).setView(R.layout.dialog_alter)
                .setText(R.id.dialog_message, "" + msg)
                .setWidth(ScreenUtils.getScreenWidth() * 5 / 7)
                .setText(R.id.dialog_cancel, cancelText)
                .setText(R.id.dialog_submit, submitText)

                .setClick(R.id.dialog_submit, new OnDialogViewClickListener() {
                    @Override
                    public void onClick(final RadishDialog dialog, View view) {
                        if (callBack != null) {
                            callBack.callBack(dialog, view);
                        }
                        dialog.dismiss();
                    }
                })
                .setClick(R.id.dialog_cancel, new OnDialogViewClickListener() {
                    @Override
                    public void onClick(RadishDialog dialog, View view) {
                        dialog.dismiss();
                    }
                }).show();
    }








    public interface OnAlterDialogCallBack {
        void callBack(RadishDialog dialog, View view);
    }
}
