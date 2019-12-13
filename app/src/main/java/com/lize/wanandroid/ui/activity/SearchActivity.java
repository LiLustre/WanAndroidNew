package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
        binding.searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    //hideKeyboard(EditText);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    return true;
                }

                return false;
            }
        });
    }

    public void onBackClick(View view) {
        finish();
    }
}
