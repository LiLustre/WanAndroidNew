package com.lize.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.lize.wanandroid.core.http.error.ErrorCode;
import com.lize.wanandroid.core.http.WanAndroidRespone;
import com.lize.wanandroid.core.http.retrofit.callback.BaseCallback;
import com.lize.wanandroid.model.navi.Navi;
import com.lize.wanandroid.model.navi.NaviModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Lize
 * on 2019/10/16
 */
public class NaviClassifyViewModel extends IBaseViewModel {

    private MutableLiveData<List<Navi>> naviListData = new MutableLiveData<>();
    private NaviModel naviModel;

    public NaviClassifyViewModel() {
        naviModel = new NaviModel();

    }

    public MutableLiveData<List<Navi>> getNaviListData() {
        return naviListData;
    }

    public void getArticleClassifyList() {
        naviModel.getArticleClassiftList(new BaseCallback<WanAndroidRespone<List<Navi>>>() {
            @Override
            public void onSuccess(Call<WanAndroidRespone<List<Navi>>> call, Response<WanAndroidRespone<List<Navi>>> response) {
                if (response.body().getErrorCode() == ErrorCode.ERROR_CODE_OK) {
                    naviListData.setValue(response.body().getData());
                }
            }

            @Override
            public void onError(Call<WanAndroidRespone<List<Navi>>> call, Throwable error) {

            }

            @Override
            public void onFailed(Call<WanAndroidRespone<List<Navi>>> call, Response<WanAndroidRespone<List<Navi>>> response) {

            }
        });

    }
}
