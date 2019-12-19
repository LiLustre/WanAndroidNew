package com.lize.wanandroid.model.navi;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.request.NaviRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;

import java.util.List;

public class NaviModel {

    private NaviRequest naviRequest ;

    public NaviModel() {
        this.naviRequest = new NaviRequest();
    }

    public void getArticleClassiftList(BaseCallback<WanAndroidRespone<List<Navi>>> responeCallBack){
        naviRequest.getNaviList(responeCallBack);
    }
}
