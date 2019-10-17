package com.lize.wanandroid.http.retrofit.factory;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.lize.wanandroid.http.retrofit.BaseCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;


import retrofit2.CallAdapter;
import retrofit2.Retrofit;



/**
 * created by lize on 2018/11/30
 */
public class BaseCallAdapterFactory extends CallAdapter.Factory {
    private final @Nullable Executor callbackExecutor;

    public BaseCallAdapterFactory() {
        this.callbackExecutor =defaultCallbackExecutor();
    }

    public static BaseCallAdapterFactory create() {
        return new BaseCallAdapterFactory();
    }

    /**
     * 获取CallAdapter
     * @param returnType APIService 方法返回的类型
     * @param annotations
     * @param retrofit
     * @return
     */

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 根据 returnType 返回 类型 例如 Class<?>,
        //  Type 是所有类型的 父类
        Class<?> rawType = getRawType(returnType);
        if (rawType != BaseCall.class){
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Call return type must be parameterized as BaseCall<Foo> or BaseCall<? extends Foo>");
        }
        //获取 返回类型中 的泛型
         Type respooneType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new BaseCallAdapter<>(respooneType,callbackExecutor);
    }

    public Executor defaultCallbackExecutor() {
        return new MainThreadExecutor();
    }
    static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override public void execute(Runnable r) {
            handler.post(r);
        }
    }
}
