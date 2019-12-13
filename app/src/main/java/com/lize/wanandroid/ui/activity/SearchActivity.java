package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    public void onBackClick(View view) {
        finish();
    }
}
