package com.xinzu.xiaodou.util;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.xinzu.xiaodou.base.BaseGActivity;
import com.xinzu.xiaodou.base.OnPermissionCallBack;

public class CommonOperate {
    public static void callPhone(final BaseGActivity activity, String number) {
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        activity.getPermission(new OnPermissionCallBack() {
            @Override
            public void permissionPass(String[] permissions) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                activity.startActivity(intent);
            }

            @Override
            public void permissionRefuse(String[] permissions) {

            }
        }, permissions);
    }
}
