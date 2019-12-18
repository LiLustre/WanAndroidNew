package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.search.DaoSession;
import com.lize.wanandroid.model.search.HotKey;
import com.lize.wanandroid.model.search.SearchHistory;
import com.lize.wanandroid.model.search.SearchHistoryModel;
import com.lize.wanandroid.model.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchViewModel extends IBaseViewModel {

    public MutableLiveData<List<HotKey>> hotKeyList = new MutableLiveData<>();
    private SearchModel searchModel;
    private SearchHistoryModel searchHistoryModel;
    public MutableLiveData<List<ArticleBean>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableLoadMore = new MutableLiveData<Boolean>();
    private int pageCount;

    public SearchViewModel() {
        this.searchHistoryModel = new SearchHistoryModel();
        this.searchModel = new SearchModel();
        curPage.setValue(0);
        enableLoadMore.setValue(false);
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void getHotKeyList() {
        searchModel.getHotKeyList(new BaseCallback<WanAndroidRespone<List<HotKey>>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<List<HotKey>>> call, Response<WanAndroidRespone<List<HotKey>>> response) {
                List<HotKey> hotKeys = hotKeyList.getValue();
                if (hotKeys == null) {
                    hotKeys = new ArrayList<>();
                }
                hotKeys.clear();
                hotKeys.addAll(response.body().getData());
                hotKeyList.setValue(hotKeys);
            }

            @Override
            public void onError(Call<WanAndroidRespone<List<HotKey>>> call, Throwable error) {

            }

            @Override
            public void onFailed(Call<WanAndroidRespone<List<HotKey>>> call, Response<WanAndroidRespone<List<HotKey>>> response) {

            }
        });
    }

    public void searchArticle(final boolean isRefresh, String key) {
        if (isRefresh) {
            curPage.setValue(0);
            pageCount = 0;
        }
        if (pageCount >= curPage.getValue()) {
            searchModel.searchArticle(String.valueOf(curPage.getValue()), key, new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    List<ArticleBean> articleListLDValue = listMutableLiveData.getValue();
                    if (articleListLDValue == null) {
                        articleListLDValue = new ArrayList<>();
                    }
                    if (isRefresh) {
                        articleListLDValue.clear();
                    }
                    articleListLDValue.addAll(response.body().getData().getDatas());
                    listMutableLiveData.setValue(articleListLDValue);
                    SearchViewModel.this.pageCount = response.body().getData().getPageCount();
                    if (pageCount > curPage.getValue()) {
                        int curPageInt = curPage.getValue();
                        curPage.setValue(curPageInt + 1);
                    }
                    if (pageCount == curPage.getValue()) {
                        enableLoadMore.setValue(false);
                    } else {
                        enableLoadMore.setValue(true);
                    }
                }

                @Override
                public void onError(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Throwable error) {

                }

                @Override
                public void onFailed(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {

                }
            });
        } else {
            enableLoadMore.setValue(false);
        }
    }

    public List<SearchHistory> getSearchHistory(DaoSession daoSession) {
        return searchHistoryModel.getSearchList(daoSession);
    }

    public void addSearchHistory(DaoSession daoSession, SearchHistory searchHistory) {
        searchHistoryModel.addSearchHistory(searchHistory, daoSession);
    }

    public void clearSearchHistory(DaoSession daoSession) {
        searchHistoryModel.clearAllSearchHistory(daoSession);
    }
}
