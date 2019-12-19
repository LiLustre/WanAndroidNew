package com.lize.wanandroid.core.http.request;

import com.lize.wanandroid.config.URLConfig;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.core.http.retrofit.HttpManager;
import com.lize.wanandroid.core.http.service.UserLoginService;
import com.lize.wanandroid.model.login.User;
import com.lize.wanandroid.util.ValueUtil;

import java.util.HashMap;
import java.util.Map;

public class UserLoginRequest {

    /**
     * 登录
     *
     * @param userName
     * @param pwd
     * @param callback
     */
    public void login(String userName, String pwd, BaseCallback<WanAndroidRespone<User>> callback) {
        Map<String, Object> map = new HashMap<>();
        if (ValueUtil.isStringValid(userName)) {
            map.put("username", userName);
        }
        if (ValueUtil.isStringValid(pwd)) {
            map.put("password", pwd);
        }
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(UserLoginService.class)
                .login(map)
                .enqueue(callback);
    }


    /**
     * 退出登录
     *
     * @param callback
     */
    public void logout(BaseCallback<WanAndroidRespone<Object>> callback) {
        HttpManager.getRetrofit(URLConfig.getInstance().getServerUrl())
                .create(UserLoginService.class)
                .logout()
                .enqueue(callback);
    }
}
