package com.radish.framelibrary.skin.config;


public class SkinConfig {
    //    sp 的文件名称
    public static final String SKIN_CONFIG_NAME = "radish_skin_config_name";

    //    保存当前皮肤文件的路径的名称
    public static final String SKIN_CONFIG_PATH_NAME = "radish_skin_config_path_name";

    //    更新皮肤或恢复皮肤成功
    public static final int SKIN_CHANGE_SUCCESS = 1;

    //    更新皮肤失败
    public static final int SKIN_CHANGE_FAIL = -1;

    //    本地皮肤文件为空
    public static final int SKIN_CHANGE_FILE_NULL = -2;

    //    皮肤文件路径有误
    public static final int SKIN_CHANGE_PATH_ERROR = -3;

    //    本地皮肤文件有误
    public static final int SKIN_CHANGE_FILE_ERROR = -4;
}
