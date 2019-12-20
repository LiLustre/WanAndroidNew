package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivitySearchBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.search.HotKey;
import com.lize.wanandroid.model.search.SearchHistory;
import com.lize.wanandroid.model.search.SearchModel;
import com.lize.wanandroid.ui.adapter.ArticleListAdapter;
import com.lize.wanandroid.ui.adapter.HotKeyAdapter;
import com.lize.wanandroid.ui.adapter.SearchHistoryAdapter;
import com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper.LoadmoreWrapper;
import com.lize.wanandroid.util.SystemKeyBroadUtil;
import com.lize.wanandroid.util.ToastUtil;
import com.lize.wanandroid.util.ValueUtil;
import com.lize.wanandroid.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    private SearchViewModel searchViewModel;
    private HotKeyAdapter hotKeyAdapter;
    private List<HotKey> hotKeyList;
    private List<SearchHistory> searchHistoryList;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private ArticleListAdapter articleListAdapter;
    private LoadmoreWrapper loadmoreWrapper;
    private String key;

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
                    key = binding.searchEt.getText().toString();
                    searchArticle(key, true);
                    return true;
                }

                return false;
            }
        });
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    binding.pageLl.setVisibility(View.VISIBLE);

                    binding.articleRv.setVisibility(View.GONE);
                } else {
                    binding.pageLl.setVisibility(View.GONE);
                    articleBeanList.clear();
                    if (articleListAdapter != null) {
                        articleListAdapter.notifyDataSetChanged();
                    }
                    binding.articleRv.setVisibility(View.VISIBLE);
                }
            }
        });
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.hotKeyList.observe(this, new Observer<List<HotKey>>() {
            @Override
            public void onChanged(List<HotKey> hotKeys) {
                hotKeyList = hotKeys;
                initHotKeyAdapter();
            }
        });
        searchViewModel.enableLoadMore.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (loadmoreWrapper != null) {
                    loadmoreWrapper.setEnableLoadMore(aBoolean);
                    loadmoreWrapper.notifyDataSetChanged();
                }
            }
        });
        searchViewModel.getHotKeyList();
        searchHistoryList = searchViewModel.getSearchHistory(getDaoSession());
        initSearchHistoryAdapter();
        searchViewModel.listMutableLiveData.observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                articleBeanList.clear();
                articleBeanList.addAll(articleBeans);
                initSearchAdapter();
            }
        });
    }

    private void initSearchAdapter() {
        binding.articleRv.setVisibility(View.VISIBLE);
        if (ValueUtil.isListValid(articleBeanList)) {
            if (articleListAdapter == null) {
                articleListAdapter = new ArticleListAdapter(articleBeanList);
                articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {

                    }


                    @Override
                    public void onCollectionClick(int pos) {

                    }
                });
                binding.articleRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                loadmoreWrapper = new LoadmoreWrapper(articleListAdapter, this);
                loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        searchViewModel.searchArticle(false, key);
                    }
                });
                binding.articleRv.setAdapter(loadmoreWrapper);
            } else {
                articleListAdapter.notifyDataSetChanged();
            }
        } else {
            ToastUtil.show(this, "暂无搜索结果");
        }
    }

    private void initSearchHistoryAdapter() {
        if (ValueUtil.isListValid(searchHistoryList)) {
            binding.searchHistoryTitleRl.setVisibility(View.VISIBLE);
            binding.searchHistoryLine.setVisibility(View.VISIBLE);
            binding.searchHistoryRv.setVisibility(View.VISIBLE);
            SearchHistoryAdapter searchHistoryAdapter = new SearchHistoryAdapter(searchHistoryList);
            searchHistoryAdapter.setListener(new SearchHistoryAdapter.Listener() {
                @Override
                public void onItemClick(int pos) {
                    key = searchHistoryList.get(pos).getKey();
                    binding.searchEt.setText(key);
                    searchArticle(key, true);
                }
            });
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this,FlexDirection.ROW,FlexWrap.WRAP);

            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
            binding.searchHistoryRv.setLayoutManager(flexboxLayoutManager);
            binding.searchHistoryRv.setAdapter(searchHistoryAdapter);
        } else {
            binding.searchHistoryTitleRl.setVisibility(View.GONE);
            binding.searchHistoryLine.setVisibility(View.GONE);
            binding.searchHistoryRv.setVisibility(View.GONE);
        }
    }

    private void searchArticle(String searchKey, boolean isRefresh) {
        SystemKeyBroadUtil.hideKeyBoard(this, binding.searchEt);
        View view = getWindow().getCurrentFocus();
        if (view != null) {
            view.clearFocus();
        }
        if (!TextUtils.isEmpty(searchKey)) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setKey(searchKey);
            searchHistory.setCreateTime(System.currentTimeMillis());
            searchViewModel.addSearchHistory(getDaoSession(), searchHistory);
            searchViewModel.searchArticle(isRefresh, searchKey);
        } else {
            ToastUtil.show(this, "请输入要搜索的内容");
        }
    }

    private void initHotKeyAdapter() {
        hotKeyAdapter = new HotKeyAdapter(hotKeyList);
        hotKeyAdapter.setListener(new HotKeyAdapter.Listener() {
            @Override
            public void onItemClick(int pos) {
                binding.searchEt.setText(hotKeyList.get(pos).getName());
                key = hotKeyList.get(pos).getName();
                searchArticle(key, true);
            }
        });
        binding.hotSearchRv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.hotSearchRv.setAdapter(hotKeyAdapter);
    }

    public void onDelHistoryClick(View view) {
        searchViewModel.clearSearchHistory(getDaoSession());
        searchHistoryList = searchViewModel.getSearchHistory(getDaoSession());
        initSearchHistoryAdapter();
    }

    public void onBackClick(View view) {
        finish();
    }
}
