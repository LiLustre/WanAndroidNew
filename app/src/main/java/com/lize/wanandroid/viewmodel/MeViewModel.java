package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.core.http.request.UserLoginRequest;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.login.UserManager;
import com.lize.wanandroid.model.user.UserInfo;
import com.lize.wanandroid.model.user.UserInfoModel;

import retrofit2.Call;
import retrofit2.Response;

public class MeViewModel extends IBaseViewModel {
    public MutableLiveData<String> nickName = new MutableLiveData<>();
    public MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    public MutableLiveData<Boolean> userLoginStatus = new MutableLiveData<>();
    private UserInfoModel userInfoModel;
    private UserLoginRequest userLoginRequest;

    public MeViewModel() {
        userInfoModel = new UserInfoModel();
        userLoginRequest = new UserLoginRequest();
        userLoginStatus.setValue(UserManager.getInstance().getLoginStatus());
        if (UserManager.getInstance().getLoginStatus()) {
            if (UserManager.getInstance().getUser() != null) {
                nickName.setValue(UserManager.getInstance().getUser().getNickname());
            } else {
            }
        } else {
            nickName.setValue("点击登录");
        }
    }

    public void getUserInfo() {
        userInfoModel.getUserInfo(new BaseCallback<WanAndroidRespone<UserInfo>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<UserInfo>> call, Response<WanAndroidRespone<UserInfo>> response) {
                if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                    userInfo.setValue(response.body().getData());
                } else {

                }

            }

            @Override
            public void onFailed(Call<WanAndroidRespone<UserInfo>> call, Response<WanAndroidRespone<UserInfo>> response) {

            }

            @Override
            public void onError(Call<WanAndroidRespone<UserInfo>> call, Throwable error) {

            }
        });
    }

    public void logout() {
        userLoginRequest.logout(new BaseCallback<WanAndroidRespone<Object>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<Object>> call, Response<WanAndroidRespone<Object>> response) {
                if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                    UserManager.getInstance().logout();
                    userLoginStatus.setValue(UserManager.getInstance().getLoginStatus());
                }
            }

            @Override
            public void onFailed(Call<WanAndroidRespone<Object>> call, Response<WanAndroidRespone<Object>> response) {

            }

            @Override
            public void onError(Call<WanAndroidRespone<Object>> call, Throwable error) {

            }
        });
    }

}
