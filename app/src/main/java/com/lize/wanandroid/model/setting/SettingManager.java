package com.lize.wanandroid.model.setting;

import com.lize.wanandroid.constants.AppConstants;
import com.lize.wanandroid.preferencehelper.PreferenceHelper;

public class SettingManager {
    private static SettingManager instance;
    private Boolean isNoImage;

    public static SettingManager getInstance() {
        if (instance == null) {
            synchronized (SettingManager.class) {
                if (instance == null) {
                    instance = new SettingManager();
                }
            }
        }
        return instance;
    }


    public void setNoImageModel(boolean isNoImage) {
        this.isNoImage = isNoImage;
        PreferenceHelper.getInstance().putAppBoolean(AppConstants.NO_IMAGE_MODEL, isNoImage);
    }

    public boolean getNoImageModel() {
        if (isNoImage == null) {
            isNoImage = PreferenceHelper.getInstance().getAppBoolean(AppConstants.NO_IMAGE_MODEL, false);
        }
        return isNoImage;
    }

}
