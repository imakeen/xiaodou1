package com.radish.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import com.radish.baselibrary.R;
import com.radish.baselibrary.dialog.OnDialogViewClickListener;
import com.radish.baselibrary.dialog.RadishDialog;

import java.util.List;

/**
 * <pre>
 *     author : radish
 *     e-mail : 15703379121@163.com
 *     time   : 2019/4/7
 *     desc   :
 * </pre>
 */
public class AppUtil {

    public static void checkWchat(Context context, AppCheckCallback callBack) {
        boolean appInstalled = isAppInstalled(context, "com.tencent.mm");
        if (!appInstalled) {
            showBrowserUpdateDialog(context, "微信", "market://details?id=" + "com.tencent.mm");
        } else {
            callBack.callBack();
        }
    }

    public static void checkAli(Context context, AppCheckCallback callBack) {
        boolean appInstalled = isAppInstalled(context, "com.eg.android.AlipayGphone");
        if (!appInstalled) {
            showBrowserUpdateDialog(context, "支付宝", "market://details?id=" + "com.eg.android.AlipayGphone");
        } else {
            callBack.callBack();
        }
    }

    public static void checkQQ(Context context, AppCheckCallback callBack) {
        boolean appInstalled = isAppInstalled(context, "com.tencent.mobileqq");
        if (!appInstalled) {
            showBrowserUpdateDialog(context, "QQ", "market://details?id=" + "com.tencent.mobileqq");
        } else {
            callBack.callBack();
        }
    }

    public interface AppCheckCallback {
        void callBack();
    }

    /**
     * 更新提示框
     *
     * @param context
     * @param downPath
     */
    public static void showBrowserUpdateDialog(final Context context, final String message, final String downPath) {
        new RadishDialog.Builder(context).setView(R.layout.dialog_alter)
                .setText(R.id.dialog_message, "" + "您还没有安装" + message + "\n请先安装" + message)
                .setClick(R.id.dialog_submit, new OnDialogViewClickListener() {
                    @Override
                    public void onClick(final RadishDialog dialog, View view) {
                        try {
                            //安装
                            Uri uri = Uri.parse(downPath);
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(goToMarket);
                        } catch (Exception e) {
                            ToastUtil.showShort("请到应用商店安装" + message);
                            e.printStackTrace();
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

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
