package com.xhs.baselibrary.net.loaddown;

import com.xhs.baselibrary.BuildConfig;
import com.xhs.baselibrary.init.BaseParamsClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/05/2019.
 * description:
 */
public class UpdateRetrofitUtils {

    private static final int DEFAULT_TIMEOUT = 15;


    public static Retrofit getRetrofit(String baseUrl, JsDownloadListener listener) {
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
