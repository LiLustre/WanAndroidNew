package com.lize.wanandroid.model.login;

import com.google.gson.GsonBuilder;
import com.lize.wanandroid.constants.UserConstants;
import com.lize.wanandroid.preferencehelper.PreferenceHelper;

public class UserManager {

    private static UserManager instance;

    private String userName;
    private String pwd;
    private User user;
    private boolean loginStatus;

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public void saveAccount(String account) {
        this.userName = account;
        PreferenceHelper.getInstance().putAppString(UserConstants.ACCOUNT, account);
    }

    public void savePwd(String pwd) {
        this.pwd = pwd;
        PreferenceHelper.getInstance().putAppString(UserConstants.PWD, pwd);
    }

    public void saveLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
        PreferenceHelper.getInstance().putAppBoolean(UserConstants.LOGIN_STATUS, loginStatus);
    }

    public void logout() {
        loginStatus = false;
        saveLoginStatus(loginStatus);
        user = null;
        saveUser(null);

    }

    public boolean getLoginStatus() {
        if (loginStatus) {
            return loginStatus;
        }
        loginStatus = PreferenceHelper.getInstance().getAppBoolean(UserConstants.LOGIN_STATUS, false);
        return loginStatus;
    }

    public void saveUser(User user) {
        this.user = user;
        String userStr = null;
        if (user != null) {
            userStr = new GsonBuilder().create().toJson(user);
        }
        PreferenceHelper.getInstance().putAppString(UserConstants.USER, userStr);

    }

    public User getUser() {
        if (user != null) {
            return user;
        }
        String userStr = PreferenceHelper.getInstance().getAppString(UserConstants.USER, null);
        user = new GsonBuilder().create().fromJson(userStr, User.class);
        return user;
    }

    public String getAccount() {
        if (this.userName != null) {
            return userName;
        }
        userName = PreferenceHelper.getInstance().getAppString(UserConstants.ACCOUNT, null);
        return userName;
    }


    public String getPwd() {
        if (this.pwd != null) {
            return pwd;
        }
        pwd = PreferenceHelper.getInstance().getAppString(UserConstants.PWD, null);
        return pwd;
    }

}
