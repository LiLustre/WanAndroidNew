package com.lize.wanandroid.core.http.retrofit.callback;

import com.lize.wanandroid.core.http.WanAndroidRespone;

import retrofit2.Call;
import retrofit2.Response;

public abstract class WanAndroidCallBack<T> extends BaseCallback<WanAndroidRespone<T>> {

    @Override
    public void onSuccess(Call<WanAndroidRespone<T>> call, Response<WanAndroidRespone<T>> response) {
    }


}
