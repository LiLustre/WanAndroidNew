package com.lize.wanandroid.core.http.retrofit.interceptor;

import com.google.gson.GsonBuilder;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.model.login.UserManager;
import com.lize.wanandroid.util.ValueUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oriRequest = chain.request();
        Response oriResponse = chain.proceed(oriRequest);
        if (oriResponse.isSuccessful()) {
            if (oriResponse.body() != null && oriResponse.body().contentType() != null) {
                MediaType mediaType = oriResponse.body().contentType();
                String string = oriResponse.body().string();
                if (ValueUtil.isStringValid(string)) {
                    try {
                        WanAndroidRespone wanAndroidRespone = new GsonBuilder().create().fromJson(string, WanAndroidRespone.class);
                        if (wanAndroidRespone != null) {
                            if (wanAndroidRespone.getErrorCode() == ErrorCode.NO_LOGIN) {
                                UserManager userManager = new UserManager();
                                //将登录状态重置为未登录
                                userManager.saveLoginStatus(false);
                            }
                        }
                    } catch (Exception e) {
                        ResponseBody responseBody = ResponseBody.create(mediaType, string);
                        return oriResponse.newBuilder().body(responseBody).build();
                    }
                }
                ResponseBody responseBody = ResponseBody.create(mediaType, string);
                return oriResponse.newBuilder().body(responseBody).build();
            } else {
                return oriResponse;
            }
        }
        return oriResponse;
    }
}
