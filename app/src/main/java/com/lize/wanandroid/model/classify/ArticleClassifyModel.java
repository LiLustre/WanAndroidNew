package com.lize.wanandroid.model.classify;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.request.ArticleClassifyRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/22
 */
public class ArticleClassifyModel {
    private ArticleClassifyRequest articleClassifyRequest ;

    public ArticleClassifyModel() {
        this.articleClassifyRequest = new ArticleClassifyRequest();
    }

    public void getArticleClassiftList(BaseCallback<WanAndroidRespone<List<ArticleClassify>>> responeCallBack){
        articleClassifyRequest.getArticleClassifyList(responeCallBack);
    }
}
