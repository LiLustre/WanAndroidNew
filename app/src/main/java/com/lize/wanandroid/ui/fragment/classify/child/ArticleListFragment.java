package com.lize.wanandroid.ui.fragment.classify.child;


import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentArticleListBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.ui.adapter.ArticleListAdapter;
import com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper.LoadmoreWrapper;
import com.lize.wanandroid.viewmodel.ArticleListViewModel;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class ArticleListFragment extends LazyBaseFragment<FragmentArticleListBinding> {
    private static ArticleListFragment instance = null;
    private ArticleListViewModel articleListViewModel;
    private ArticleListAdapter articleListAdapter;
    private LoadmoreWrapper loadmoreWrapper;
    private String classifyID;


    public static ArticleListFragment newInstance(String classifyID) {
        ArticleListFragment instance = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("classifyID", classifyID);
        instance.setArguments(bundle);
        return instance;
    }

    public static ArticleListFragment getInstance() {
        if (instance == null) {
            instance = new ArticleListFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {

        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        classifyID = getArguments().getString("classiftID");
        bindind.setLifecycleOwner(this);
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        bindind.articleSrl.setRefreshing(true);
        bindind.articleSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindind.articleSrl.setRefreshing(false);
                setupAdapter();
            }
        });
        articleListViewModel.getArticleList(true, classifyID);
        articleListViewModel.getListMutableLiveData().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                bindind.articleSrl.setRefreshing(false);
                setupAdapter();
            }
        });
    }

    private void setupAdapter() {
        if (bindind.articleRv.getAdapter() == null) {
            articleListAdapter = new ArticleListAdapter(articleListViewModel.getListMutableLiveData().getValue());
            articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {

                }

                @Override
                public void onMoreClick(int pos) {

                }

                @Override
                public void onCollectionClick(int pos) {

                }
            });
            bindind.articleRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            loadmoreWrapper = new LoadmoreWrapper(articleListAdapter, getActivity());
            loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    articleListViewModel.getArticleList(false, classifyID);
                }
            });
            bindind.articleRv.setAdapter(loadmoreWrapper);
        } else {
            loadmoreWrapper.notifyDataSetChanged();
        }

    }


}
