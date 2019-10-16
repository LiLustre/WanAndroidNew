package com.lize.wanandroid.http.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lize.wanandroid.http.retrofit.factory.BaseCallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sky on 2017-11-3.
 */

public class HttpManager {

    private static HttpManager instance;

    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    //单例模式
    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public static Retrofit getRetrofit(String baseUrl) {
        return getInstance().createRetrofit(baseUrl);
    }

    public Retrofit createRetrofit(String baseUrl) {
        if (isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) {
            return retrofitMap.get(baseUrl);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpConfig.getNormalHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(getGsonConverterFactory())
                .addCallAdapterFactory(BaseCallAdapterFactory.create())
                .build();

        retrofitMap.put(baseUrl, retrofit);
        return retrofit;

    }


    private GsonConverterFactory getGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return GsonConverterFactory.create(gson);
    }

    private boolean isEmpty(String baseUrl) {
        return baseUrl == null || baseUrl.isEmpty();
    }

    /*public  T createService (String baseUrl, Class serviceClass){

      return (T) getInstance().getRetrofit(baseUrl).create(serviceClass);
    }*/

}
