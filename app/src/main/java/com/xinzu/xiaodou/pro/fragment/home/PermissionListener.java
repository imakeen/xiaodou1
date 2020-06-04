package com.xinzu.xiaodou.pro.fragment.home;

import java.util.List;

/**
 * Created by Administrator on 2020/1/21 18:17
 */
public interface PermissionListener {
    void onGranted();//已授权

    void onDenied(List<String> deniedPermission);//未授权
}
