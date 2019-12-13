package com.lize.wanandroid.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.PhotoViewDialogBinding;
import com.lize.wanandroid.util.DisplayUtil;


public class PhotoViewDialog extends Dialog {
    private PhotoViewDialogBinding binding;
    private String url;

    public PhotoViewDialog(Context context, int themeResId, String url) {
        super(context, themeResId);
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.photo_view_dialog, null, false);
        setContentView(binding.getRoot());
        binding.iv.setMinimumScale(0.5f);
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Glide.with(getContext()).load(url).into(binding.iv);
    }

    public void setUrl(String url) {
        this.url = url;
        Glide.with(getContext()).load(url).into(binding.iv);

    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
