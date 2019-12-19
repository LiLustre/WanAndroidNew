package com.lize.wanandroid.core.http.service;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.BaseCall;
import com.lize.wanandroid.model.navi.Navi;

import java.util.List;

import retrofit2.http.GET;

public interface NaviService {

    @GET("navi/json")
    BaseCall<WanAndroidRespone<List<Navi>>> getNaviList();

}
