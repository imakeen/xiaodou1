package com.xinzu.xiaodou.http;


import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.MyApp;
import com.xinzu.xiaodou.util.DownloadListener;
import com.xinzu.xiaodou.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hzy on 2019/1/10
 * 网络请求
 *
 * @author hzy
 */
public class HttpManager {

    private Retrofit mRetrofit;
    private HashMap<Class, Retrofit> mServiceHashMap = new HashMap<>();
    private ConcurrentHashMap<Class, Object> cachedApis = new ConcurrentHashMap<>();
    private PersistentCookieJar cookieJar;


    public HttpManager() {
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new
                SharedPrefsCookiePersistor(MyApp.getInstance()));
        // init okhttp 3 logger
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        // int okhttp

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .cookieJar(cookieJar)
                .build();

        // int retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ServiceUrl)
                .client(client)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();

        mServiceHashMap.put(ApiService.class, mRetrofit);

    }

    public void downLoadapk(String url, final File file, final DownloadListener callBak) {
        MyApp.apiService(ApiService.class).downLoadApk(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        LogUtils.e("----写入文件：-----");
//                        请求完成以后=----写入文件
                        FileUtils.writeFileSDcard(responseBody, file, callBak);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        callBak.OnError(e);
                        LogUtils.e("下载请求错误：" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("下载文件----onComplete");
                    }
                });
    }


    public <T> T getService(Class<T> clz) {
        Object obj = cachedApis.get(clz);
        if (obj != null) {
            return (T) obj;
        } else {
            Retrofit retrofit = mServiceHashMap.get(clz);
            if (retrofit != null) {
                T service = retrofit.create(clz);
                cachedApis.put(clz, service);
                return service;
            } else {
                return null;
            }
        }
    }
}