package com.shenxuesong.weblog.net;

import com.github.florent37.retrojsoup.RetroJsoup;

import org.jsoup.Jsoup;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class JsoupHelper {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (Jsoup.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    private static <T> T createApi(Class<T> clazz, String webUrl) {
        return new RetroJsoup.Builder()
                .url(webUrl)
                .client(new OkHttpClient())
                .build()
                .create(clazz);
    }

    public static ServiceApi getBlogFeed() {
        return createApi(ServiceApi.class, API.BLOG_BASE_URL);
    }
}