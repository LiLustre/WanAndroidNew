package com.lize.wanandroid.http.service;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCall;
import com.lize.wanandroid.model.article.ArticleBean;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author Lize
 * on 2019/10/16
 */
public interface ArticleService {

    @GET("article/list/{page}/json")
    BaseCall<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> getNewPostsList(@Path("page") String page);

    @GET("article/top/json")
    BaseCall<WanAndroidRespone<List<ArticleBean>>> getTopPostsList();


    @GET("article/listproject/{page}/json")
    BaseCall<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> getProjectList(@Path("page") String page);


    @GET("article/list/{page}/json")
    BaseCall<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> getArticleList(@Path("page") String page, @QueryMap Map<String,String> map);


    
}
