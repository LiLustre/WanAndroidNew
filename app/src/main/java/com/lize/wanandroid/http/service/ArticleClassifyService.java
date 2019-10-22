package com.lize.wanandroid.http.service;

import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCall;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.classify.ArticleClassify;

import java.util.List;

import retrofit2.http.GET;

/**
 * @author Lize
 * on 2019/10/22
 */
public interface ArticleClassifyService {

    @GET("tree/json")
    BaseCall<WanAndroidRespone<List<ArticleClassify>>> getArticleClassifyList();


}
