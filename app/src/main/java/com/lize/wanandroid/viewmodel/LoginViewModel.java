package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.HttpResErrorPaser;
import com.lize.wanandroid.core.http.request.UserLoginRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.login.User;
import com.lize.wanandroid.model.login.UserManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginViewModel extends IBaseViewModel {
    private UserLoginRequest loginRequest;

    private UserManager userManager = new UserManager();
    public MutableLiveData<User> loginResultLiveData = new MutableLiveData<User>();
    public MutableLiveData<String> loginErrMsg = new MutableLiveData<String>();
    public MutableLiveData<String> account = new MutableLiveData<String>();
    public MutableLiveData<String> pwd = new MutableLiveData<String>();

    public LoginViewModel() {
        this.loginRequest = new UserLoginRequest();
        account.setValue(userManager.getAccount());
        pwd.setValue(userManager.getPwd());
    }

    public void login(String userName, final String pwd) {
        loginRequest.login(userName, pwd, new BaseCallback<WanAndroidRespone<User>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<User>> call, Response<WanAndroidRespone<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getErrorCode() == 0) {
                            User user = response.body().getData();
                            if (user != null) {
                                userManager.saveAccount(user.getUsername());
                                userManager.savePwd(pwd);
                                userManager.saveLoginStatus(true);
                                userManager.saveUser(user);
                            }
                            loginResultLiveData.setValue(user);
                        } else {
                            loginErrMsg.setValue(response.body().getErrorMsg());
                        }
                    } else {
                        loginErrMsg.setValue("登录失败");
                    }
                }
            }


            @Override
            public void onFailed(Call<WanAndroidRespone<User>> call, Response<WanAndroidRespone<User>> response) {
                try {
                    loginErrMsg.setValue(HttpResErrorPaser.parseResError(response.code(), response.errorBody().string()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call<WanAndroidRespone<User>> call, Throwable error) {
                loginErrMsg.setValue(HttpResErrorPaser.parseResException(error));
            }
        });
    }
}
