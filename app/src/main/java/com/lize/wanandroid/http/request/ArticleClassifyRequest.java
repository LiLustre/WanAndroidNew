package com.lize.wanandroid.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCallback;
import com.lize.wanandroid.http.retrofit.HttpManager;
import com.lize.wanandroid.http.service.ArticleClassifyService;
import com.lize.wanandroid.http.service.ArticleService;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.classify.ArticleClassify;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/22
 */
public class ArticleClassifyRequest {

    /**
     * 获取文章分类
     *
     * @param responeCallBack
     */
    public void getArticleClassifyList(BaseCallback<WanAndroidRespone<List<ArticleClassify>>> responeCallBack) {

        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleClassifyService.class)
                .getArticleClassifyList()
                .enqueue(responeCallBack);

    }
}
