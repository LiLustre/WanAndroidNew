package com.lize.wanandroid.http.service;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCall;
import com.lize.wanandroid.model.article.ArticleBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Lize
 * on 2019/10/16
 */
public interface ArticleService {

    @GET("article/list/{page}/json")
    BaseCall<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> getNewPostsList(@Path("page") String page);

    @GET("article/top/json")
    BaseCall<WanAndroidRespone<List<ArticleBean>>> getTopPostsList();


}
