package com.lize.wanandroid.http.retrofit;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public abstract class BaseResponeCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        callResult(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        onError(call, t);
    }

    private void callResult(Call<T> call, Response<T> response) {
        if (call.isCanceled()) {
            onCancele(call);
        }
        if (response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailed(call, response);
        }
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);

    public abstract void onError(Call<T> call, Throwable error);

    public abstract void onFailed(Call<T> call, Response<T> response);

    public void onCancele(Call<T> call) {

    }
}
