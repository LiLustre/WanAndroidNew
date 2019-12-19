package com.lize.wanandroid.core.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.core.http.WanAndroidPageData;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.core.http.retrofit.HttpManager;
import com.lize.wanandroid.core.http.service.SearchService;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.search.HotKey;
import com.lize.wanandroid.util.ValueUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRequest {

    public void getHotKeyList(BaseCallback<WanAndroidRespone<List<HotKey>>> responeCallBack) {
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(SearchService.class)
                .getHotKeyList()
                .enqueue(responeCallBack);
    }

    public void searchArticle(String page, String key, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {
        Map<String, String> map = new HashMap<>();
        if (ValueUtil.isStringValid(key)) {
            map.put("k", key);
        }
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(SearchService.class)
                .searchArticle(page, map)
                .enqueue(responeCallBack);

    }
}
