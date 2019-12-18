package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityLoginBinding;
import com.lize.wanandroid.model.login.User;
import com.lize.wanandroid.util.ToastUtil;
import com.lize.wanandroid.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private LoginViewModel loginViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.account.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.accountEt.setText(s);
            }
        });
        loginViewModel.pwd.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.pwdEt.setText(s);
            }
        });
        loginViewModel.loginResultLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                ToastUtil.show(LoginActivity.this, "登录成功");
                finish();
            }
        });
        loginViewModel.loginErrMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtil.show(LoginActivity.this, s);
            }
        });
    }

    public void onLoginClick(View view) {
        String account = binding.accountEt.getText().toString();
        String pwd = binding.pwdEt.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.show(this, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show(this, "请输入密码");
            return;
        }
        loginViewModel.login(account, pwd);

    }
}
