package com.theone.mvvm.core.app.util.glide;


import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 描述:
 * <p>
 * 拦截器
 * Created by allens on 2018/1/8.
 */

public class GlideProgressInterceptor implements Interceptor {

    public static final Map<String, IGlideProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(Object url, IGlideProgressListener listener) {
        if (url instanceof GlideUrl) {
            GlideUrl glideUrl = (GlideUrl) url;
            addListener(glideUrl.getCacheKey(), listener);
        } else {
            addListener((String) url, listener);
        }
    }

    //入注册下载监听
    public static void addListener(String url, IGlideProgressListener listener) {
        if (!LISTENER_MAP.containsKey(url))
            LISTENER_MAP.put(url, listener);
    }

    public static void removeListener(Object object) {
        if (object instanceof GlideUrl) {
            GlideUrl glideUrl = (GlideUrl) object;
            removeListener(glideUrl.getCacheKey());
        } else {
            removeListener((String) object);
        }
    }

    //取消注册下载监听
    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        Response newResponse = response.newBuilder().body(new GlideProgressResponseBody(url, body)).build();
        return newResponse;
    }
}