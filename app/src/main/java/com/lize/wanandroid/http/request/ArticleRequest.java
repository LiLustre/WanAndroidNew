package com.lize.wanandroid.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCallback;
import com.lize.wanandroid.http.retrofit.HttpManager;
import com.lize.wanandroid.http.service.ArticleService;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.util.ValueUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleRequest {

    /**
     * 获取首页文章
     *
     * @param page
     * @param responeCallBack
     */
    public void getNewArticleList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {

        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleService.class)
                .getNewPostsList(page)
                .enqueue(responeCallBack);

    }

    /**
     * 获取置顶文章
     *
     * @param responeCallBack
     */
    public void getTopArticleList(BaseCallback<WanAndroidRespone<List<ArticleBean>>> responeCallBack) {

        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleService.class)
                .getTopPostsList()
                .enqueue(responeCallBack);

    }


    /**
     * 获取项目文章
     *
     * @param responeCallBack
     */
    public void getProjectList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {

        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleService.class)
                .getProjectList(page)
                .enqueue(responeCallBack);

    }


    /**
     * 获取文章列表
     *
     * @param responeCallBack
     */
    public void getArticleList(String page, String cid, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {
        Map<String, String> map = new HashMap<>();
        if (ValueUtil.isStringValid(cid)) {
            map.put("cid", cid);
        }
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(ArticleService.class)
                .getArticleList(page, map)
                .enqueue(responeCallBack);

    }

}
