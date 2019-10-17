package com.lize.wanandroid.http.retrofit;


import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public class BaseCall<T> implements Call<T>{

    private final Call<T> delegate;
    Executor callbackExecutor;

    public BaseCall(Executor callbackExecutor, Call<T> call) {
        this.callbackExecutor = callbackExecutor;
        this.delegate = call;
    }

    public void enqueue(Object tag, Callback<T> callback) {
        enqueue(callback);
    }
    @Override
    public void enqueue(final Callback<T> callback) {
        delegate.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Call<T> call, final Response<T> response) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (delegate.isCanceled()) {
                            // Emulate OkHttp's behavior of throwing/delivering an IOException on cancellation.
                            callback.onFailure(BaseCall.this, new IOException("Canceled"));
                        } else {
                            callback.onResponse(BaseCall.this, response);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call,final Throwable t) {
                callbackExecutor.execute(new Runnable() {
                    @Override public void run() {
                        callback.onFailure(BaseCall.this, t);
                    }
                });
            }
        });
    }

    @Override
    public Response execute() throws IOException {
        return delegate.execute();
    }


    @Override
    public boolean isExecuted() {
        return delegate.isExecuted();
    }


    @Override
    public void cancel() {
        delegate.cancel();
    }


    @Override
    public boolean isCanceled() {
        return delegate.isCanceled();
    }

    @Override
    public Call clone() {
        return delegate.clone();
    }


    @Override
    public Request request() {
        return delegate.request();
    }
}
