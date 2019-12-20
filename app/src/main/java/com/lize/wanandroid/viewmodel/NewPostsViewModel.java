package com.lize.wanandroid.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.article.ArticleModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Lize
 * on 2019/10/16
 */
public class NewPostsViewModel extends IBaseViewModel {

    public MutableLiveData<Boolean> liekReq = new MutableLiveData<>();
    public MutableLiveData<Boolean> unliekReq = new MutableLiveData<>();
    private MutableLiveData<List<ArticleBean>> articleListLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadResult = new MutableLiveData<>();
    private ArticleModel articleModel;
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();
    private int pageCount;

    public NewPostsViewModel() {
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

    public void getArticleList(final boolean isRefresh) {
        if (isRefresh) {
            curPage.setValue(0);
        }
        if (pageCount >= curPage.getValue()) {
            articleModel.getIndexArticle(curPage.getValue(), new ArticleModel.OnIndexArticltLoadListener() {
                @Override
                public void onLoadSuccess(List<ArticleBean> articleBeans, int pageCount) {
                    loadResult.setValue(true);

                    List<ArticleBean> articleListLDValue = articleListLD.getValue();
                    if (articleListLDValue == null) {
                        articleListLDValue = new ArrayList<>();
                    }
                    if (isRefresh) {
                        articleListLDValue.clear();
                    }
                    articleListLDValue.addAll(articleBeans);
                    articleListLD.setValue(articleListLDValue);
                    NewPostsViewModel.this.pageCount = pageCount;
                    if (pageCount > curPage.getValue()) {
                        int curPageInt = curPage.getValue();
                        curPage.setValue(curPageInt + 1);
                    }
                }

                @Override
                public void onLoadError() {
                    loadResult.setValue(false);
                }
            });

        } else {
            //没有下一页数据
        }
    }

    public void likeArticle(String articleID, final int pos) {
        List<ArticleBean> articleListLDValue = articleListLD.getValue();

        if (articleListLDValue.get(pos).isCollect()){
            articleModel.unLikeArticle(articleID, new BaseCallback<WanAndroidRespone>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone> call, Response<WanAndroidRespone> response) {
                    if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                        unliekReq.setValue(Boolean.TRUE);
                        List<ArticleBean> articleListLDValue = articleListLD.getValue();
                        articleListLDValue.get(pos).setCollect(false);
                    }
                }

                @Override
                public void onFailed(Call<WanAndroidRespone> call, Response<WanAndroidRespone> response) {
                    unliekReq.setValue(false);
                }

                @Override
                public void onError(Call<WanAndroidRespone> call, Throwable error) {
                    unliekReq.setValue(false);
                }
            });
        }else {
            articleModel.likeArticle(articleID, new BaseCallback<WanAndroidRespone>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone> call, Response<WanAndroidRespone> response) {
                    if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                        liekReq.setValue(Boolean.TRUE);
                        List<ArticleBean> articleListLDValue = articleListLD.getValue();
                        articleListLDValue.get(pos).setCollect(true);
                    }
                }

                @Override
                public void onFailed(Call<WanAndroidRespone> call, Response<WanAndroidRespone> response) {
                    liekReq.setValue(false);
                }

                @Override
                public void onError(Call<WanAndroidRespone> call, Throwable error) {
                    liekReq.setValue(false);
                }
            });
        }

    }

}
