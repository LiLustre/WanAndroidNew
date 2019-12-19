package com.lize.wanandroid.core.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.core.http.retrofit.HttpManager;
import com.lize.wanandroid.core.http.service.ArticleClassifyService;
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
