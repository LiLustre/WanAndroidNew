package com.lize.wanandroid.ui.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.SearchHistoryItemBinding;
import com.lize.wanandroid.model.search.SearchHistory;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;

import java.util.List;

public class SearchHistoryAdapter extends DataBindingRecyclerAdapter {
    private List<SearchHistory> searchHistoryList;
    private Listener listener;

    public SearchHistoryAdapter(List<SearchHistory> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setSearchHistoryList(List<SearchHistory> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        SearchHistoryItemBinding itemBinding = (SearchHistoryItemBinding) binding;
        itemBinding.historyKeyTv.setText(searchHistoryList.get(position).getKey());
        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.search_history_item;
    }

    @Override
    public int getItemCount() {
        return searchHistoryList == null ? 0 : searchHistoryList.size();
    }

    public interface Listener {
        void onItemClick(int pos);

    }


}
