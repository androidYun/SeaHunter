package com.xhs.baselibrary.net.loaddown;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/05/2019.
 * description:
 */
public class JsDownloadInterceptor implements Interceptor {

    private JsDownloadListener downloadListener;

    public JsDownloadInterceptor(JsDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new JsResponseBody(response.body(), downloadListener)).build();
    }
}
