package com.xhs.baselibrary.net.interceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request originRequest = chain.request();
        Request.Builder newRequest = originRequest.newBuilder();
        RequestBody body = originRequest.body();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
            if (charset != null) {
                //读取原请求参数内容
                String requestParams = buffer.readString(charset);
                try {
                    //重新拼凑请求体
                    JSONObject jsonObject = new JSONObject(requestParams);
                    for (Map.Entry<String, Integer> entry : getCommonParams().entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                    RequestBody newBody = RequestBody.create(body.contentType(), jsonObject.toString());
                    newRequest.post(newBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        response = chain.proceed(newRequest.build());
        return response;
    }

    private Map<String, Integer> getCommonParams() {
        Map<String, Integer> map = new HashMap<>();
        map.put("user_id", 1);
        return map;
    }
}
