package com.radish.framelibrary.http;

import com.radish.baselibrary.utils.StringUtils;

/**
 * @作者 radish
 * @创建日期 2018/12/26 1:52 PM
 * @邮箱 15703379121@163.com
 * @描述 网络缓存实体类
 */
public class CacheBean {
    private String urlKey;
    private String resultJson;


    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public CacheBean(String urlKey, String resultJson) {
        this.urlKey = urlKey;
        this.resultJson = resultJson;
    }

    public CacheBean() {
    }

    @Override
    public String toString() {
        return StringUtils.obj2Json(this);
    }
}
