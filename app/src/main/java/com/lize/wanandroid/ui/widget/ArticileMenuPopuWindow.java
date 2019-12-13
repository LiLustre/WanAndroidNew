package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.ArticileDetailMenuLayoutBinding;
import com.lize.wanandroid.util.SharesUtils;

public class ArticileMenuPopuWindow extends PopupWindow {
    ArticileDetailMenuLayoutBinding binding;
    private Listener listener;

    public ArticileMenuPopuWindow(Context context) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.articile_detail_menu_layout, null, false);
        setContentView(binding.getRoot());
        setOutsideTouchable(true);
       setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.likeArticileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLike();
                }
                dismiss();
            }
        });
        binding.shareArticileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onShare();
                }
                dismiss();
            }
        });

        binding.copyAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCopy();
                }
                dismiss();
            }
        });

        binding.webOpenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOpenWeb();
                }
                dismiss();
            }
        });

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void onShare();

        void onLike();

        void onCopy();

        void onOpenWeb();

    }
}
