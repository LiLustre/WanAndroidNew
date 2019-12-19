package com.lize.wanandroid.core.http.service;

import com.lize.wanandroid.core.http.WanAndroidPageData;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.BaseCall;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.search.HotKey;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchService {

    @GET("hotkey/json")
    BaseCall<WanAndroidRespone<List<HotKey>>> getHotKeyList();

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    BaseCall<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> searchArticle(@Path("page") String page, @FieldMap Map<String, String> map);
}
