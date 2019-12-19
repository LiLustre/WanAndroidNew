package com.lize.wanandroid.model.user;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.request.UserInfoRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;

public class UserInfoModel {
    private UserInfoRequest userInfoRequest;

    public UserInfoModel() {
        userInfoRequest = new UserInfoRequest();
    }

    public void getUserInfo(BaseCallback<WanAndroidRespone<UserInfo>> baseCallback) {
        userInfoRequest.getUserInfo(baseCallback);
    }
}
