package com.lize.wanandroid.http.retrofit.paser;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 * 响应解析
 */
public class HttpParser<T> implements IHttpParser<T> {
    @Override
    public String parserFailedResponse(Call<T> call, Response<T> response) {
        String msg = null;
        if (response != null) {
            int code = response.code();
            switch (code) {
                case 404:
                    msg = "请求资源不存在";
                    break;
                case 500:
                    msg = "服务器错误";
                    break;
                case 401:
                case 403:
                    msg = "身份认证失败";
                    break;
                case 422:
                    msg = "请求参数错误";
                    break;
                default:
                    msg = "服务器异常";
                    break;
            }
        }
        return msg;
    }

    @Override
    public String parserThrowable(Call<T> call, Throwable t) {
        String msg;
        if (t instanceof IOException) {
            msg = "网络异常";
        } else {
            msg = "意外错误：" + t.getMessage();
        }

        return msg;
    }
}
