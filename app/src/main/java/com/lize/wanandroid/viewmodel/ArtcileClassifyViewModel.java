package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.http.error.ErrorCode;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.model.classify.ArticleClassifyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArtcileClassifyViewModel extends IBaseViewModel {

    private MutableLiveData<List<ArticleClassify>> articleClassify = new MutableLiveData<>();
    private ArticleClassifyModel articleClassifyModel;

    public ArtcileClassifyViewModel() {
        articleClassifyModel = new ArticleClassifyModel();

    }

    public MutableLiveData<List<ArticleClassify>> getArticleClassify() {
        return articleClassify;
    }

    public void getArticleClassifyList() {
        articleClassifyModel.getArticleClassiftList(new BaseCallback<WanAndroidRespone<List<ArticleClassify>>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<List<ArticleClassify>>> call, Response<WanAndroidRespone<List<ArticleClassify>>> response) {
                if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                    articleClassify.setValue(response.body().getData());
                }
            }

            @Override
            public void onError(Call<WanAndroidRespone<List<ArticleClassify>>> call, Throwable error) {

            }

            @Override
            public void onFailed(Call<WanAndroidRespone<List<ArticleClassify>>> call, Response<WanAndroidRespone<List<ArticleClassify>>> response) {

            }
        });
    }
}
