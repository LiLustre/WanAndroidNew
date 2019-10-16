package com.lize.wanandroid.http.retrofit.paser;


import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public interface IHttpParser<T> {


    String parserFailedResponse(Call<T> call, Response<T> response);

    String parserThrowable(Call<T> call, Throwable t);

}
