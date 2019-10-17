package com.lize.wanandroid.model.article;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.request.ArticleRequest;
import com.lize.wanandroid.http.retrofit.BaseCallback;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleModel {
    private ArticleRequest articleRequest;

    public ArticleModel() {
        this.articleRequest = new ArticleRequest();
    }

    public void getNewPostList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getNewArticleList(page, baseResponeCallBack);
    }
}
