package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivitySettingBinding;
import com.lize.wanandroid.util.CacheUtil;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        binding.cacheSizeTv.setText(CacheUtil.getCacheSize(this));
    }

    public void onBackClick(View view) {
        finish();

    }

    public void onCacheClick(View view) {
        CacheUtil.clearAllCache(this);
        binding.cacheSizeTv.setText(CacheUtil.getCacheSize(this));
    }
}
