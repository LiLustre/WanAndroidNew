package com.lize.wanandroid.preferencehelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.lize.wanandroid.base.BaseApplication;
import com.lize.wanandroid.constants.AppConstants;
import com.lize.wanandroid.util.ValueUtil;

public class PreferenceHelper {
    private SharedPreferences appPreferences;
    private static PreferenceHelper instance;

    public PreferenceHelper() {
        this.appPreferences = getAppPreference();
    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            synchronized (PreferenceHelper.class) {
                if (instance == null) {
                    instance = new PreferenceHelper();
                }
            }
        }
        return instance;
    }


    public SharedPreferences getAppPreference() {
        return getPrivatePreference(AppConstants.WANANDROID_PREFERENCE);
    }


    public static SharedPreferences getPrivatePreference(String preferenceName) {
        if (ValueUtil.isStringValid(preferenceName)) {
            return BaseApplication.applicationContext.getSharedPreferences(AppConstants.WANANDROID_PREFERENCE, Context.MODE_PRIVATE);
        }
        return null;
    }

    public void putAppString(String key, String value) {
        putString(appPreferences, key, value);
    }

    public String getAppString(String key, String defValue) {
     return    getString(appPreferences, key, defValue);
    }

    public void putAppBoolean(String key, boolean value) {
        putBoolean(appPreferences, key, value);
    }

    public boolean getAppBoolean(String key, boolean defValue) {
       return getBoolean(appPreferences, key, defValue);
    }

    public void putAppInt(String key, int value) {
        putInt(appPreferences, key, value);
    }

    public int getAppInt(String key, int defValue) {
       return getInt(appPreferences, key, defValue);
    }

    public void putAppFloat(String key, float value) {
        putFloat(appPreferences, key, value);
    }

    public void getAppFloat(String key, float defValue) {
        getFloat(appPreferences, key, defValue);
    }

    public void putAppLong(String key, long value) {
        putLong(appPreferences, key, value);
    }

    public long getAppLong(String key, long defValue) {
       return getLong(appPreferences, key, defValue);
    }

    public void putString(SharedPreferences sharedPreferences, String key, String value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(key, value).apply();
        }
    }

    public String getString(SharedPreferences sharedPreferences, String key, String defalutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defalutValue);
        }
        return null;
    }

    public void putBoolean(SharedPreferences sharedPreferences, String key, boolean value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean(key, value).apply();
        }
    }

    public Boolean getBoolean(SharedPreferences sharedPreferences, String key, boolean defalutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defalutValue);
        }
        return null;
    }

    public void putInt(SharedPreferences sharedPreferences, String key, int value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(key, value).apply();
        }
    }

    public int getInt(SharedPreferences sharedPreferences, String key, int defalutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defalutValue);
        }
        return defalutValue;
    }

    public void putFloat(SharedPreferences sharedPreferences, String key, float value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putFloat(key, value).apply();
        }
    }

    public float getFloat(SharedPreferences sharedPreferences, String key, float defalutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, defalutValue);
        }
        return defalutValue;
    }

    public void putLong(SharedPreferences sharedPreferences, String key, long value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong(key, value).apply();
        }
    }

    public long getLong(SharedPreferences sharedPreferences, String key, long defalutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, defalutValue);
        }
        return defalutValue;
    }


}
