package com.lize.wanandroid.ui.fragment.index.child;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentNewProjectsBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.ui.adapter.ArticleListAdapter;
import com.lize.wanandroid.ui.adapter.ProjectListAdapter;
import com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper.LoadmoreWrapper;
import com.lize.wanandroid.viewmodel.NewPostsViewModel;
import com.lize.wanandroid.viewmodel.NewProjectViewModel;

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
public class NewProjectsFragment extends LazyBaseFragment<FragmentNewProjectsBinding> {
    private static NewProjectsFragment instance = null;
    private NewProjectViewModel newProjectViewModel;
    private ProjectListAdapter projectListAdapter;
    private LoadmoreWrapper loadmoreWrapper;

    public static NewProjectsFragment getInstance() {
        if (instance == null) {
            instance = new NewProjectsFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_projects;
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        bindind.setLifecycleOwner(this);
        newProjectViewModel = ViewModelProviders.of(this).get(NewProjectViewModel.class);
        newProjectViewModel.getProjectList(true);
        bindind.newProjectSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newProjectViewModel.getProjectList(true);
            }
        });
        bindind.newProjectSrl.setRefreshing(true);
        newProjectViewModel.getArticleListLD().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                bindind.newProjectSrl.setRefreshing(false);
                setupAdapter();
            }
        });
    }

    private void setupAdapter() {
        if (loadmoreWrapper == null) {
            projectListAdapter = new ProjectListAdapter(newProjectViewModel.getArticleListLD().getValue());
            projectListAdapter.setOnItemClickListener(new ProjectListAdapter.OnItemClickListener() {
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
            bindind.newProjectRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            loadmoreWrapper = new LoadmoreWrapper(projectListAdapter, getActivity());
            loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    newProjectViewModel.getProjectList(false);
                }
            });
            bindind.newProjectRv.setAdapter(loadmoreWrapper);
        } else {
            loadmoreWrapper.notifyDataSetChanged();
        }

    }
}
