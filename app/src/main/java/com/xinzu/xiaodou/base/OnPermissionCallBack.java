package com.xinzu.xiaodou.base;

/**
 * <pre>
 *     author : radish
 *     e-mail : 15703379121@163.com
 *     time   : 2019/4/29
 *     desc   :
 * </pre>
 */
public interface OnPermissionCallBack {
    void permissionPass(String[] permissions);

    void permissionRefuse(String[] permissions);
}
