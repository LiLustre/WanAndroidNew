package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.http.ErrorCode;
import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCallback;
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

    private MutableLiveData<List<ArticleBean>> articleListLD = new MutableLiveData<>();
    private ArticleModel articleModel;
    private int curPage;
    private int pageCount;

    public NewPostsViewModel() {
        articleModel = new ArticleModel();
    }

    public MutableLiveData<List<ArticleBean>> getArticleListLD() {
        return articleListLD;
    }


    public void getArticleList() {
        if (pageCount >= curPage) {

            articleModel.getNewPostList(String.valueOf(curPage), new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    if (response.body() != null && response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {

                        List<ArticleBean> articleBeans = articleListLD.getValue();
                        if (articleBeans == null) {
                            articleBeans = new ArrayList<>();
                        }
                        articleBeans.addAll(response.body().getData().getDatas());
                        articleListLD.setValue(articleBeans);
                        pageCount = response.body().getData().getPageCount();
                        if (response.body().getData().getPageCount() > curPage) {
                            curPage++;
                        }
                    } else {

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
            //没有下一页数据
        }
    }


}
