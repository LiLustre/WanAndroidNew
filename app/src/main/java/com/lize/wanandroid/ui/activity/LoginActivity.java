package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }
}
