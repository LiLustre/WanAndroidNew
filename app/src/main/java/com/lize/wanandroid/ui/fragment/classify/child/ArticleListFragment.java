package com.lize.wanandroid.ui.fragment.classify.child;


import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentArticleListBinding;
import com.lize.wanandroid.databinding.FragmentNewPostsBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.ui.adapter.ArticleListAdapter;
import com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper.LoadmoreWrapper;
import com.lize.wanandroid.viewmodel.ArticleListViewModel;
import com.lize.wanandroid.viewmodel.NewPostsViewModel;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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


    public static ArticleListFragment newInstance() {

        return new ArticleListFragment();
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

    }

    @Override
    protected void onFragmentFirstVisible() {
        //  classifyID = getArguments().getString("classiftID");
        classifyID = "60";
        bindind.setLifecycleOwner(this);
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        articleListViewModel.getArticleList(classifyID);
        articleListViewModel.getListMutableLiveData().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                setupAdapter();
            }
        });
    }

    private void setupAdapter() {
        if (loadmoreWrapper == null) {
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
                    articleListViewModel.getArticleList(classifyID);
                }
            });
            bindind.articleRv.setAdapter(loadmoreWrapper);
        } else {
            loadmoreWrapper.notifyDataSetChanged();
        }

    }


}
