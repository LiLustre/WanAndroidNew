package com.lize.wanandroid.ui.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Lize
 * on 2019/10/17
 */
public abstract class DataBindingRecyclerAdapter extends RecyclerView.Adapter<DataBindingRecyclerAdapter.VH> {


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutId(viewType), parent, false);
        return new VH(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        onBindView( binding, position, holder);
        binding.executePendingBindings();
    }

    protected abstract void onBindView(ViewDataBinding binding, int position, VH holder);

    public abstract int getItemLayoutId(int viewType);


   public static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

}