package com.lize.wanandroid.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.BaseApplication;
import com.lize.wanandroid.util.statusbar.StatusBarCompat;

/**
 * @author Lize
 * on 2019/10/12
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    public T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.status_bar_color));
        init(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initView(savedInstanceState);
    }

    public void init(@Nullable Bundle savedInstanceState) {

    }

    protected abstract void initView(@Nullable Bundle savedInstanceState);


    public abstract int getLayoutId();


    public void exitApplication() {
        ((BaseApplication) getApplication()).exitApplication();
    }

    /**
     * 返回到主页 时是否被Finish
     *
     * @return
     */
    public boolean isFinishOnBackToMain() {
        return true;
    }

    /**
     * 回退到主页
     */
    public void backToMainActivity() {
        ((BaseApplication) getApplication()).backToMainActivity();
    }

}
