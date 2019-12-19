package com.lize.wanandroid.core.http.service;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.BaseCall;
import com.lize.wanandroid.model.login.User;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserLoginService {

    @FormUrlEncoded
    @POST("user/login")
    BaseCall<WanAndroidRespone<User>> login(@FieldMap Map<String, Object> map);

    @GET("user/logout/json")
    BaseCall<WanAndroidRespone<Object>> logout();
}
