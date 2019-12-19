package com.lize.wanandroid.core.http.service;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.BaseCall;
import com.lize.wanandroid.model.user.UserInfo;

import retrofit2.http.GET;

public interface UserInfoService {

    @GET("lg/coin/userinfo/json")
    BaseCall<WanAndroidRespone<UserInfo>> getUserInfo();

}
