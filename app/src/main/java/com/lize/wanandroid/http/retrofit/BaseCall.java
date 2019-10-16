package com.lize.wanandroid.http.retrofit;


import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public    class BaseCall<T> {

    private final Call<T> call;

    public BaseCall(Call<T> call) {
        this.call = call;
    }

    public void enqueue(Object tag, BaseResponeCallBack<T> callback){
        call.enqueue(callback);
    }

    public void enqueue( BaseResponeCallBack<T> callback){
        call.enqueue(callback);
    }

    public Response execute() throws IOException {
        return call.execute();
    }

    public boolean isExecuted() {
        return call.isExecuted();
    }


    public void cancel() {
        call.cancel();
    }


    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call clone() {
        return call.clone();
    }


    public Request request() {
        return call.request();
    }
}
