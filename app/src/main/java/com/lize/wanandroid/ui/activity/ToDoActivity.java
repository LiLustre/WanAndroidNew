package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityToDoBinding;

public class ToDoActivity extends BaseActivity<ActivityToDoBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_to_do;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }
}
