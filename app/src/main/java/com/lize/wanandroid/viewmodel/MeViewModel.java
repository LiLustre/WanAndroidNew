package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.login.UserManager;
import com.lize.wanandroid.model.user.UserInfo;
import com.lize.wanandroid.model.user.UserInfoModel;

import retrofit2.Call;
import retrofit2.Response;

public class MeViewModel extends IBaseViewModel {
    public MutableLiveData<String> nickName = new MutableLiveData<>();
    public MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private UserManager userManager;
    private UserInfoModel userInfoModel;

    public MeViewModel() {
        userInfoModel = new UserInfoModel();
        userManager = new UserManager();
        if (userManager.getLoginStatus()) {
            if (userManager.getUser() != null) {
                nickName.setValue(userManager.getUser().getNickname());
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

}
