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

                }
            });

        } else {
            //没有下一页数据
        }
    }


}
