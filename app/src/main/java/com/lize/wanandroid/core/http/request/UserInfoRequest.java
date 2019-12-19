package com.lize.wanandroid.core.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.core.http.retrofit.HttpManager;
import com.lize.wanandroid.core.http.service.UserInfoService;
import com.lize.wanandroid.model.user.UserInfo;

public class UserInfoRequest {

    public void getUserInfo(BaseCallback<WanAndroidRespone<UserInfo>> baseCallback){
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(UserInfoService.class)
                .getUserInfo()
                .enqueue(baseCallback);
    }

}
