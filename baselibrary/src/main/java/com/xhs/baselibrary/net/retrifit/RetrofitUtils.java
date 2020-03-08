package com.xhs.baselibrary.net.retrifit;


import com.xhs.baselibrary.BuildConfig;
import com.xhs.baselibrary.init.BaseParamsClient;
import com.xhs.baselibrary.net.converter.LenientGsonConverterFactory;
import com.xhs.baselibrary.net.interceptor.AuthorInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 28/04/2019.
 * description:
 */
public class RetrofitUtils {
    private static final long TIME_OUT = 30;

    private static Retrofit retrofit;

    /**
     * 这个是 baseUrl的 Retrofit
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://122.114.13.86:8080/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }



    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(new AuthorInterceptor())
            .addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Versions", BuildConfig.VERSION_NAME)
                        .addHeader("Authorization", BaseParamsClient.getInstance().getToken())
                        .addHeader("Cookie", "add cookies here").build();
                return chain.proceed(request);
            })
            .addInterceptor(getLogInterceptor())
            .build();



    public static void addHead(String headKey, String headValue) {
        httpClient = httpClient.newBuilder().addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(headKey, headValue).build();
            return chain.proceed(request);
        }).build();
    }


    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
