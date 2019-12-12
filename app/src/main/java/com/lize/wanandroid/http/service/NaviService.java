package com.lize.wanandroid.http.service;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCall;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.navi.Navi;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NaviService {

    @GET("navi/json")
    BaseCall<WanAndroidRespone<List<Navi>>> getNaviList();

}
