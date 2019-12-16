package com.lize.wanandroid.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.base.checker.ApplicationChecker;
import com.lize.wanandroid.greendao.SqliteDaoMasterHelper;
import com.lize.wanandroid.model.search.DaoMaster;
import com.lize.wanandroid.model.search.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;

/**
 * @author Lize
 * on 2019/10/12
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static Context applicationContext;
    private ArrayList<Activity> activities;
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationChecker.markApplicationCreated();
        initAppConfig();
        initActivityBackRecordStack();
        initDataBase();
    }

    private void initAppConfig() {
        applicationContext = getApplicationContext();
    }



    /**
     * 初始化数据库
     */
    private void initDataBase() {

        DaoMaster.DevOpenHelper helper = new SqliteDaoMasterHelper(this, ENCRYPTED ? "wanandroid-db-encrypted" : "wanandroid-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 初始化activity回退栈
     */
    private void initActivityBackRecordStack() {
        activities = new ArrayList<Activity>();
        registerActivityLifecycleCallbacks(this);
    }

    public void backToMainActivity() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity instanceof BaseActivity) {
                if (((BaseActivity) activity).isFinishOnBackToMain()) {
                    activity.finish();
                }
            } else {
                throw new UnsupportedOperationException("only instance extends BaseActivity can call this method");
            }
        }
    }

    public void exitApplication() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            activity.finish();
        }
        activities.clear();
        killProcess();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }

    public void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //---------------------------------Activity生命周期监听--------------------------------------
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }
}
