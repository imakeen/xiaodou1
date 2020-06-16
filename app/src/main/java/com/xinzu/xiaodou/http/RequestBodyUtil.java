package com.xinzu.xiaodou.http;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @作者 radish
 * @创建日期 2019/1/17 2:13 PM
 * @邮箱 15703379121@163.com
 * @描述 指示器的adapter
 */
public class RequestBodyUtil {
    /**
     * 将文件路径数组封装为{@link List< MultipartBody.Part>}
     *
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *            同一个name值，实际情况中有可能需要多个
     */

    public static List<MultipartBody.Part> filesToMultipartBodyParts(String key, List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 将参数封装成requestBody形式上传参数
     *
     * @param param 参数
     * @return RequestBody
     */
    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    /**
     * 将参数封装成requestBody形式上传参数
     * json
     *
     * @param hashMap 参数
     * @return RequestBody
     */
    public static RequestBody jsonRequestBody(HashMap<String, String> hashMap) {
        Gson gson = new Gson();
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(hashMap));
    }

    public static RequestBody RequestBody(String bean) {

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bean);
    }

    /**
     * 将文件进行转换
     *
     * @param param 为文件类型
     * @return
     */
    public static RequestBody convertToRequestBodyMap(File param) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), param);
        return requestBody;
    }

    /**
     * 将文件进行转换
     *
     * @param param 为文件类型
     * @return
     */
    public static MultipartBody.Part filesToMultipartBodyPart(String key, File param) {
        RequestBody requestBody = convertToRequestBodyMap(param);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, param.getName(), requestBody);
        return part;
    }
}
