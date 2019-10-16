package com.lize.wanandroid.http.request;

import com.lize.wanandroid.http.ResponeData;
import com.lize.wanandroid.http.retrofit.BaseResponeCallBack;
import com.lize.wanandroid.http.retrofit.HttpManager;
import com.lize.wanandroid.http.service.ArticleService;
import com.lize.wanandroid.model.article.ArticleBean;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleRequest {

    public void getNewArticleList(String page, BaseResponeCallBack<ResponeData<List<ArticleBean>>> responeCallBack) {
        HttpManager.getRetrofit("")
                .create(ArticleService.class)
                .getNewPostsList(page)
                .enqueue(responeCallBack);

    }

}
