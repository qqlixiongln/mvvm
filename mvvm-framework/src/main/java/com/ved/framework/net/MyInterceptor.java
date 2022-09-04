package com.ved.framework.net;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class MyInterceptor implements Interceptor {
    private Map<String, String> headers;

    public MyInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }

        List<String> headerValues = request.headers("url_name");
        if (headerValues != null && headerValues.size() > 0) {
            String headerValue = headerValues.get(0);
            HttpUrl oldHttpUrl = request.url();
            HttpUrl newBaseUrl = null;
            if("weixin".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse("https://api.weixin.qq.com/sns/oauth2/");
            }else{
                newBaseUrl = oldHttpUrl;
            }

            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            builder = builder.url(newFullUrl);
        }
        request = builder.build();
        return chain.proceed(request);
    }
}