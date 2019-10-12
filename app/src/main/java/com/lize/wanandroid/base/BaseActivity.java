package com.lize.wanandroid.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lize.wanandroid.R;
import com.lize.wanandroid.util.statusbar.StatusBarCompat;

/**
 * @author Lize
 * on 2019/10/12
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.status_bar_color));
    }



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
