package com.lize.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        for (ArticleClassify tab : articleClassifies) {
            bindind.tlTab.addTab(bindind.tlTab.newTab().setText(tab.getName()));
        }
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
        initChildTab();


    }

    private void initChildTab() {
        childArticleClassifyList.clear();
        childArticleClassifyList.addAll(parentArticleClassifyList.get(parentClassifyPos).getChildren());
        if (secondaryArticleClassifyAdapter == null) {
            secondaryArticleClassifyAdapter = new SecondaryArticleClassifyAdapter(childArticleClassifyList);
            secondaryArticleClassifyAdapter.setSelectPos(0);
            bindind.secondaryClassifyRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            bindind.secondaryClassifyRv.setAdapter(secondaryArticleClassifyAdapter);
        } else {
            secondaryArticleClassifyAdapter.setSelectPos(0);
            secondaryArticleClassifyAdapter.notifyDataSetChanged();
        }

    }
}
