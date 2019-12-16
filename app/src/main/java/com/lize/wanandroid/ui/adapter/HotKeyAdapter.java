package com.lize.wanandroid.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.HotSearchRecycleItemBinding;
import com.lize.wanandroid.model.search.HotKey;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;
import com.lize.wanandroid.util.ColorUtil;

import java.util.List;

public class HotKeyAdapter extends DataBindingRecyclerAdapter {

    private List<HotKey> hotKeyList;
    private Listener listener;

    public HotKeyAdapter(List<HotKey> hotKeyList) {
        this.hotKeyList = hotKeyList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        HotSearchRecycleItemBinding itemBinding = (HotSearchRecycleItemBinding) binding;
        itemBinding.hotKeyTv.setText(hotKeyList.get(position).getName());
        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        itemBinding.numTv.setText(String.valueOf(hotKeyList.get(position).getOrder()));
        itemBinding.numTv.setTextColor(ColorUtil.randomLightColor());
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.hot_search_recycle_item;
    }

    @Override
    public int getItemCount() {
        return hotKeyList == null ? 0 : hotKeyList.size();
    }

    public interface Listener {
        void onItemClick(int pos);
    }
}
