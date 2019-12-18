package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.article.ArticleModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ArticleListViewModel extends ViewModel {

    private ArticleModel articleModel = new ArticleModel();
    private MutableLiveData<List<ArticleBean>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();
    private MutableLiveData<Boolean> enableLoadMore = new MutableLiveData<Boolean>();
    private int pageCount;

    public ArticleListViewModel() {
        curPage.setValue(0);
        enableLoadMore.setValue(false);
    }

    public MutableLiveData<List<ArticleBean>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<Boolean> getEnableLoadMore() {
        return enableLoadMore;
    }

    public void getArticleList(final boolean isRefresh, String cid) {
        if (isRefresh) {
            curPage.setValue(0);
            pageCount = 0;
        }
        if (pageCount >= curPage.getValue()) {
            articleModel.getArticleList(String.valueOf(curPage.getValue()), cid, new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
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
                    ArticleListViewModel.this.pageCount = response.body().getData().getPageCount();
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

}
