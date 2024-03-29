package com.lize.wanandroid.core.http.retrofit;

import com.lize.wanandroid.base.BaseApplication;
import com.lize.wanandroid.core.http.cookie.CookieManager;
import com.lize.wanandroid.core.http.cookie.store.SPCookieStore;
import com.lize.wanandroid.core.http.retrofit.interceptor.AuthInterceptor;
import com.lize.wanandroid.util.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Lize
 * on 2019/10/14
 */
public class HttpConfig {
    /**
     * 网络请求超时时间毫秒
     */
    public static int DEFAULT_TIMEOUT = 30000;

    public static synchronized OkHttpClient getNormalHttpClient() {
        HttpLoggingInterceptor loggInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    LogUtils.i("OKHttp-----", message);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        loggInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//设置拦截器等级，BODY表示针对请求响应都体拦截
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggInterceptor)
                .addInterceptor(new AuthInterceptor())
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieManager(new SPCookieStore(BaseApplication.applicationContext)))
                .build();
        return okHttpClient;
    }
}
