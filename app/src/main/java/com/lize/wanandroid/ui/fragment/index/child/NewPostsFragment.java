package com.lize.wanandroid.ui.fragment.index.child;


import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentNewPostsBinding;
import com.lize.wanandroid.event.LoginEvent;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.ui.activity.WebViewActivity;
import com.lize.wanandroid.ui.adapter.ArticleListAdapter;
import com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper.LoadmoreWrapper;
import com.lize.wanandroid.util.ToastUtil;
import com.lize.wanandroid.viewmodel.NewPostsViewModel;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class NewPostsFragment extends LazyBaseFragment<FragmentNewPostsBinding> {
    private static NewPostsFragment instance = null;
    private NewPostsViewModel newPostsViewModel;
    private ArticleListAdapter articleListAdapter;
    private LoadmoreWrapper loadmoreWrapper;

    public static NewPostsFragment getInstance() {
        instance = new NewPostsFragment();
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_posts;
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        bindind.setLifecycleOwner(this);
        newPostsViewModel = ViewModelProviders.of(this).get(NewPostsViewModel.class);
        newPostsViewModel.getArticleList(true);
        bindind.newPostsSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newPostsViewModel.getArticleList(true);
            }
        });
        bindind.newPostsSrl.setRefreshing(true);
        newPostsViewModel.getArticleListLD().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {

                bindind.newPostsSrl.setRefreshing(false);
                setupAdapter();
            }
        });
        newPostsViewModel.loadResult.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    bindind.newPostsSrl.setRefreshing(false);
                    ToastUtil.show(getContext(), "数据加载失败");
                }
            }
        });
        newPostsViewModel.unliekReq.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Snackbar.make(getActivity().getWindow().getDecorView(), "取消收藏成功☺", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getActivity().getWindow().getDecorView(), "取消收藏失败o(╥﹏╥)o", Snackbar.LENGTH_SHORT).show();
                }
                setupAdapter();
            }
        });
        newPostsViewModel.liekReq.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Snackbar.make(getActivity().getWindow().getDecorView(), "收藏成功☺", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getActivity().getWindow().getDecorView(), "收藏失败o(╥﹏╥)o", Snackbar.LENGTH_SHORT).show();
                }
                setupAdapter();
            }
        });
    }

    private void setupAdapter() {
        if (loadmoreWrapper == null) {
            articleListAdapter = new ArticleListAdapter(newPostsViewModel.getArticleListLD().getValue());
            articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("loadUrl", newPostsViewModel.getArticleListLD().getValue().get(pos).getLink());
                    startActivity(intent);
                }

                @Override
                public void onCollectionClick(int pos) {
                    newPostsViewModel.likeArticle(String.valueOf(newPostsViewModel.getArticleListLD().getValue().get(pos).getId()), pos);
                }
            });
            bindind.newPostsRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            loadmoreWrapper = new LoadmoreWrapper(articleListAdapter, getActivity());
            loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    newPostsViewModel.getArticleList(false);
                }
            });
            bindind.newPostsRv.setAdapter(loadmoreWrapper);
        } else {
            loadmoreWrapper.notifyDataSetChanged();
        }

    }

}
