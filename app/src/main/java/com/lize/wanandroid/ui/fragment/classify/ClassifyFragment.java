package com.lize.wanandroid.ui.fragment.classify;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentClassifyBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.SecondaryArticleClassifyAdapter;
import com.lize.wanandroid.ui.adapter.base.FragmentAdapter;
import com.lize.wanandroid.ui.fragment.classify.child.ArticleListFragment;
import com.lize.wanandroid.viewmodel.ArtcileClassifyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding> {
    private static ClassifyFragment instance = null;
    private ArtcileClassifyViewModel artcileClassifyViewModel;
    private int parentClassifyPos = 0;
    private List<ArticleClassify> childArticleClassifyList = new ArrayList<>();
    private List<ArticleClassify> parentArticleClassifyList = new ArrayList<>();
    private SecondaryArticleClassifyAdapter secondaryArticleClassifyAdapter;
    private List<Fragment> fragments;

    public static ClassifyFragment getInstance() {
        if (instance == null) {
            instance = new ClassifyFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindind.setLifecycleOwner(this);
        artcileClassifyViewModel = ViewModelProviders.of(this).get(ArtcileClassifyViewModel.class);
        artcileClassifyViewModel.getArticleClassifyList();
        artcileClassifyViewModel.getArticleClassify().observe(this, new Observer<List<ArticleClassify>>() {
            @Override
            public void onChanged(List<ArticleClassify> articleClassifies) {
                parentArticleClassifyList = articleClassifies;
                initTab(articleClassifies);

            }
        });
    }

    private void initTab(List<ArticleClassify> articleClassifies) {
        bindind.tabLl.setVisibility(View.VISIBLE);
        parentClassifyPos = 0;
        bindind.tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                parentClassifyPos = tab.getPosition();
                initChildTab();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //initChildTab();
        fragments = new ArrayList<>();
        for (int i = 0; i < articleClassifies.size(); i++) {
            ArticleClassify tabArticleClassify = articleClassifies.get(i);
            bindind.tlTab.addTab(bindind.tlTab.newTab().setText(tabArticleClassify.getName()));
            ArticleListFragment articleListFragment = ArticleListFragment.newInstance();
            fragments.add(articleListFragment);
        }
        bindind.classifyArticleVp.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        bindind.tlTab.setupWithViewPager(bindind.classifyArticleVp);
        for (int i = 0; i < articleClassifies.size(); i++) {
            ArticleClassify tabArticleClassify = articleClassifies.get(i);
            bindind.tlTab.getTabAt(i).setText(tabArticleClassify.getName());
        }
        bindind.classifyArticleVp.setCurrentItem(parentClassifyPos);
        bindind.tlTab.getTabAt(parentClassifyPos).select();
    }

    private void initChildTab() {
        childArticleClassifyList.clear();
        childArticleClassifyList.addAll(parentArticleClassifyList.get(parentClassifyPos).getChildren());
        if (secondaryArticleClassifyAdapter == null) {
            secondaryArticleClassifyAdapter = new SecondaryArticleClassifyAdapter(childArticleClassifyList);
            secondaryArticleClassifyAdapter.setOnItemClickListener(new SecondaryArticleClassifyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    secondaryArticleClassifyAdapter.setSelectPos(pos);
                    secondaryArticleClassifyAdapter.notifyDataSetChanged();
                }
            });
            secondaryArticleClassifyAdapter.setSelectPos(0);
            bindind.secondaryClassifyRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            bindind.secondaryClassifyRv.setAdapter(secondaryArticleClassifyAdapter);
        } else {
            secondaryArticleClassifyAdapter.setSelectPos(0);
            secondaryArticleClassifyAdapter.notifyDataSetChanged();
        }

    }
}
