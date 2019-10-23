package com.lize.wanandroid.ui.fragment.classify;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
    private int childClassifyPos = 0;
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
        bindind.tabRl.setVisibility(View.VISIBLE);
        parentClassifyPos = 10;
        bindind.titleTv.setText(articleClassifies.get(parentClassifyPos).getName());
        bindind.tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                childClassifyPos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        fragments = new ArrayList<>();
        childClassifyPos = 0;
        childArticleClassifyList = articleClassifies.get(parentClassifyPos).getChildren();
        if (childArticleClassifyList.size()>3){
            bindind.childClassifyIb.setVisibility(View.VISIBLE);
        }else {
            bindind.childClassifyIb.setVisibility(View.GONE);
        }
        for (int i = 0; i < childArticleClassifyList.size(); i++) {
            ArticleClassify tabArticleClassify = childArticleClassifyList.get(i);
            bindind.tlTab.addTab(bindind.tlTab.newTab().setText(tabArticleClassify.getName()));
            ArticleListFragment articleListFragment = ArticleListFragment.newInstance(String.valueOf(tabArticleClassify.getId()));
            fragments.add(articleListFragment);
        }
        bindind.classifyArticleVp.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        bindind.classifyArticleVp.setOffscreenPageLimit(2);
        bindind.tlTab.setupWithViewPager(bindind.classifyArticleVp);
        for (int i = 0; i < childArticleClassifyList.size(); i++) {
            ArticleClassify tabArticleClassify = childArticleClassifyList.get(i);
            bindind.tlTab.getTabAt(i).setText(tabArticleClassify.getName());
        }
        bindind.classifyArticleVp.setCurrentItem(childClassifyPos);
        bindind.tlTab.getTabAt(childClassifyPos).select();
    }


}
