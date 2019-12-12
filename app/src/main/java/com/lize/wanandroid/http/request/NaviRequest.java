package com.lize.wanandroid.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.http.WanAndroidPageData;
import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCallback;
import com.lize.wanandroid.http.retrofit.HttpManager;
import com.lize.wanandroid.http.service.ArticleService;
import com.lize.wanandroid.http.service.NaviService;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.navi.Navi;

import java.util.List;

public class NaviRequest {

    /**
     * 获取导航数据
     *
     * @param responeCallBack
     */
    public void getNaviList(BaseCallback<WanAndroidRespone<List<Navi>>> responeCallBack) {
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(NaviService.class)
                .getNaviList()
                .enqueue(responeCallBack);
    }
}
