package com.lize.wanandroid.viewmodel;

import android.util.Log;

import com.lize.wanandroid.core.http.WanAndroidPageData;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.HttpResErrorPaser;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.article.ArticleModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Lize
 * on 2019/10/16
 */
public class NewProjectViewModel extends IBaseViewModel {

    private MutableLiveData<List<ArticleBean>> articleListLD = new MutableLiveData<>();
    public MutableLiveData<String> loadErrMsg = new MutableLiveData<>();
    private ArticleModel articleModel;
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();
    private int pageCount;

    public NewProjectViewModel() {
        articleModel = new ArticleModel();
        curPage.setValue(0);
    }

    public MutableLiveData<List<ArticleBean>> getArticleListLD() {
        return articleListLD;
    }

    public MutableLiveData<Integer> getCurPage() {
        return curPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void getProjectList(final boolean isRefresh) {
        if (isRefresh) {
            curPage.setValue(0);
        }
        if (pageCount >= curPage.getValue()) {
            articleModel.getProjectList(String.valueOf(curPage.getValue()), new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    List<ArticleBean> articleListLDValue = articleListLD.getValue();
                    if (articleListLDValue == null) {
                        articleListLDValue = new ArrayList<>();
                    }
                    if (isRefresh) {
                        articleListLDValue.clear();
                    }
                    articleListLDValue.addAll(response.body().getData().getDatas());
                    articleListLD.setValue(articleListLDValue);
                    NewProjectViewModel.this.pageCount = pageCount;
                    if (pageCount > curPage.getValue()) {
                        int curPageInt = curPage.getValue();
                        curPage.setValue(curPageInt + 1);
                    }
                }

                @Override
                public void onError(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Throwable error) {
                    loadErrMsg.setValue(HttpResErrorPaser.parseResException(error));
                    Log.e("getProjectList", "onFailed: " + error.getMessage());
                }

                @Override
                public void onFailed(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    Log.e("getProjectList", "onFailed: " + response.body().getErrorMsg());
                    try {
                        loadErrMsg.setValue(HttpResErrorPaser.parseResError(response.code(),response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            //没有下一页数据
        }
    }


}
