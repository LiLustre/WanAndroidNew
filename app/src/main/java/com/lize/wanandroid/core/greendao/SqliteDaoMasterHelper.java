package com.lize.wanandroid.core.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lize.wanandroid.model.search.DaoMaster;
import com.lize.wanandroid.model.search.SerachHistoryDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Lize on 2018/12/20
 */
public class SqliteDaoMasterHelper extends DaoMaster.DevOpenHelper {
    private static String TAG = SqliteDaoMasterHelper.class.getSimpleName();

    public SqliteDaoMasterHelper(Context context, String name) {
        super(context, name);
    }

    public SqliteDaoMasterHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i(TAG,"oldVersion:"+oldVersion+"----newVersion:"+newVersion);
        MigrationHelper.migrate(db, SerachHistoryDao.class);
    }
}
