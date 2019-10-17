package com.lize.wanandroid.http.retrofit.factory;


import androidx.annotation.Nullable;

import com.lize.wanandroid.http.retrofit.BaseCall;

import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by Lize on 2018/11/30
 */
public class BaseCallAdapter<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final @Nullable
    Executor callbackExecutor;

    public BaseCallAdapter(Type responseType, Executor callbackExecutor) {
        this.responseType = responseType;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    /**
     * 返回一个Call 的子类或者自己的实例
     * 将Call 构建成  Call 的子类或者自己 返回
     *
     * @param call
     * @return
     */
    @Override
    public Object adapt(Call<R> call) {
        if (callbackExecutor==null){
            return call;
        }
        return new BaseCall<R>(callbackExecutor,call);
    }
}
