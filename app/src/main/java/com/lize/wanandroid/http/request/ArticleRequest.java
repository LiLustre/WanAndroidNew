package com.lize.wanandroid.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCallback;
import com.lize.wanandroid.http.retrofit.HttpManager;
import com.lize.wanandroid.http.service.ArticleService;
import com.lize.wanandroid.model.article.ArticleBean;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleRequest {

    public void getNewArticleList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {

        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleService.class)
                .getNewPostsList(page)
                .enqueue(responeCallBack);

    }

}
