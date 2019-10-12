package com.lize.wanandroid.base;

import android.app.Application;
import android.content.Context;

import com.lize.wanandroid.base.checker.ApplicationChecker;

/**
 * @author Lize
 * on 2019/10/12
 */
public class BaseApplication extends Application {
    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationChecker.markApplicationCreated();
        initAppConfig();
    }

    private void initAppConfig() {
        applicationContext = getApplicationContext();
    }

}
