package com.lize.wanandroid.http.retrofit.callback;

import com.lize.wanandroid.http.WanAndroidRespone;
import com.lize.wanandroid.http.retrofit.BaseCall;

import retrofit2.Call;
import retrofit2.Response;

public abstract class WanAndroidCallBack<T> extends BaseCallback<WanAndroidRespone<T>> {

    @Override
    public void onSuccess(Call<WanAndroidRespone<T>> call, Response<WanAndroidRespone<T>> response) {
    }


}
