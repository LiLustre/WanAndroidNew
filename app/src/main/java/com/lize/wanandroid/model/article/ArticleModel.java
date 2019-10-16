package com.lize.wanandroid.model.article;

import com.lize.wanandroid.http.ResponeData;
import com.lize.wanandroid.http.request.ArticleRequest;
import com.lize.wanandroid.http.retrofit.BaseResponeCallBack;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleModel {
    private ArticleRequest articleRequest;

    public ArticleModel() {
        this.articleRequest = new ArticleRequest();
    }

    public void getNewPostList(String page, BaseResponeCallBack<ResponeData<List<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getNewArticleList(page, baseResponeCallBack);
    }
}
