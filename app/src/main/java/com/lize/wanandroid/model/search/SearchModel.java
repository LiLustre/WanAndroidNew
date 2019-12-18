package com.lize.wanandroid.model.search;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.request.SearchRequest;
import com.lize.wanandroid.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.article.ArticleBean;

import java.util.List;

public class SearchModel {

    private SearchRequest searchRequest;

    public SearchModel() {
        this.searchRequest = new SearchRequest();
    }

    public void getHotKeyList(BaseCallback<WanAndroidRespone<List<HotKey>>> responeCallBack) {
        searchRequest.getHotKeyList(responeCallBack);
    }

    public void searchArticle(String page, String key, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> responeCallBack) {
        searchRequest.searchArticle(page, key, responeCallBack);
    }


}
