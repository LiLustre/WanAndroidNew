package com.lize.wanandroid.model.article;

import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.core.http.WanAndroidPageData;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.request.ArticleRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Lize
 * on 2019/10/16
 */
public class ArticleModel {
    private ArticleRequest articleRequest;
    private AtomicInteger indexArticleRequestFinsihFlag = new AtomicInteger(2);
    private AtomicBoolean isRequestArticleListError = new AtomicBoolean(false);
    private int pageCount;
    private List<ArticleBean> topArticle;
    private List<ArticleBean> normalArticle;

    public ArticleModel() {
        this.articleRequest = new ArticleRequest();
    }


    public void getIndexArticle(int page, final OnIndexArticltLoadListener onIndexArticltLoadListener) {
        isRequestArticleListError.set(false);
        if (page == 0) {
            indexArticleRequestFinsihFlag.set(2);
            getToptList(new BaseCallback<WanAndroidRespone<List<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<List<ArticleBean>>> call, Response<WanAndroidRespone<List<ArticleBean>>> response) {
                    if (response.body() != null && response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                        topArticle = new ArrayList<>();
                        topArticle.addAll(response.body().getData());
                        if (indexArticleRequestFinsihFlag.get() > 0) {
                            onRequestInitDataFinish(onIndexArticltLoadListener);
                        }
                    } else {
                        isRequestArticleListError.set(true);
                        if (indexArticleRequestFinsihFlag.get() > 0) {
                            onRequestInitDataFinish(onIndexArticltLoadListener);
                        }
                    }

                }

                @Override
                public void onError(Call<WanAndroidRespone<List<ArticleBean>>> call, Throwable error) {
                    isRequestArticleListError.set(true);
                    if (indexArticleRequestFinsihFlag.get() > 0) {
                        onRequestInitDataFinish(onIndexArticltLoadListener);
                    }
                }

                @Override
                public void onFailed(Call<WanAndroidRespone<List<ArticleBean>>> call, Response<WanAndroidRespone<List<ArticleBean>>> response) {
                    isRequestArticleListError.set(true);
                    if (indexArticleRequestFinsihFlag.get() > 0) {
                        onRequestInitDataFinish(onIndexArticltLoadListener);
                    }
                }
            });
            getNewPostList(String.valueOf(page), new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    if (response.body() != null && response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                        normalArticle = new ArrayList<>();
                        normalArticle.addAll(response.body().getData().getDatas());
                        pageCount = response.body().getData().getPageCount();
                        if (indexArticleRequestFinsihFlag.get() > 0) {
                            onRequestInitDataFinish(onIndexArticltLoadListener);
                        }
                    } else {
                        isRequestArticleListError.set(true);
                        if (indexArticleRequestFinsihFlag.get() > 0) {
                            onRequestInitDataFinish(onIndexArticltLoadListener);
                        }
                    }
                }

                @Override
                public void onError(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Throwable error) {
                    isRequestArticleListError.set(true);
                    if (indexArticleRequestFinsihFlag.get() > 0) {
                        onRequestInitDataFinish(onIndexArticltLoadListener);
                    }
                }

                @Override
                public void onFailed(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    isRequestArticleListError.set(true);
                    if (indexArticleRequestFinsihFlag.get() > 0) {
                        onRequestInitDataFinish(onIndexArticltLoadListener);
                    }
                }
            });
        } else {
            getNewPostList(String.valueOf(page), new BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>>() {
                @Override
                public void onSuccess(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    if (response.body() != null && response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                        pageCount = response.body().getData().getPageCount();
                        onIndexArticltLoadListener.onLoadSuccess(response.body().getData().getDatas(), pageCount);
                    } else {
                        onIndexArticltLoadListener.onLoadError();
                    }

                }

                @Override
                public void onError(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Throwable error) {
                    onIndexArticltLoadListener.onLoadError();
                }

                @Override
                public void onFailed(Call<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> call, Response<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> response) {
                    onIndexArticltLoadListener.onLoadError();
                }
            });
        }

    }

    private void onRequestInitDataFinish(OnIndexArticltLoadListener onIndexArticltLoadListener) {
        if (indexArticleRequestFinsihFlag.get() > 0 && indexArticleRequestFinsihFlag.decrementAndGet() == 0) {
            indexArticleRequestFinsihFlag.set(-1);
            if (isRequestArticleListError.get()) {
                onIndexArticltLoadListener.onLoadError();
            } else {
                for (ArticleBean articleBean : topArticle) {
                    articleBean.setTop(true);
                }
                List<ArticleBean> indexArticleList = new ArrayList<>();
                indexArticleList.addAll(topArticle);
                indexArticleList.addAll(normalArticle);
                onIndexArticltLoadListener.onLoadSuccess(indexArticleList, pageCount);
            }
        }
    }


    public void getNewPostList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getNewArticleList(page, baseResponeCallBack);
    }

    public void getToptList(BaseCallback<WanAndroidRespone<List<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getTopArticleList(baseResponeCallBack);
    }

    public void getProjectList(String page, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getProjectList(page, baseResponeCallBack);
    }


    public void getArticleList(String page, String cid, BaseCallback<WanAndroidRespone<WanAndroidPageData<ArticleBean>>> baseResponeCallBack) {
        articleRequest.getArticleList(page, cid, baseResponeCallBack);
    }

    public interface OnIndexArticltLoadListener {
        void onLoadSuccess(List<ArticleBean> articleBeans, int pageCount);

        void onLoadError();
    }


    public void likeArticle(String articleID, BaseCallback<WanAndroidRespone> responeBaseCallback) {
        articleRequest.likeArticle(articleID, responeBaseCallback);
    }

    public void unLikeArticle(String articleID, BaseCallback<WanAndroidRespone> responeBaseCallback) {
        articleRequest.cancleLikeArticle(articleID, responeBaseCallback);
    }
}
