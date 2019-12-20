package com.lize.wanandroid.ui.fragment.classify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentClassifyBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.SecondaryArticleClassifyAdapter;
import com.lize.wanandroid.ui.adapter.base.FragmentAdapter;
import com.lize.wanandroid.ui.fragment.classify.child.ArticleListFragment;
import com.lize.wanandroid.ui.widget.dialog.SelectClassifyDialog;
import com.lize.wanandroid.viewmodel.ArtcileClassifyViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
    private SelectClassifyDialog selectClassifyDialog;
    private List<Fragment> fragments;

    public static ClassifyFragment getInstance() {

            instance = new ClassifyFragment();

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
        initViewClick();
        artcileClassifyViewModel = ViewModelProviders.of(this).get(ArtcileClassifyViewModel.class);
        artcileClassifyViewModel.getArticleClassifyList();
        artcileClassifyViewModel.getArticleClassify().observe(this, new Observer<List<ArticleClassify>>() {
            @Override
            public void onChanged(List<ArticleClassify> articleClassifies) {
                parentArticleClassifyList = articleClassifies;
                parentClassifyPos = 0;
                childClassifyPos = 0;
                initTab();

            }
        });

    }

    /**
     * 初始化点击事件
     */
    private void initViewClick() {
        //标题点击
        bindind.titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectClassifyDialog == null) {
                    selectClassifyDialog = new SelectClassifyDialog(getContext(), R.style.Dialog, parentArticleClassifyList);
                }
                selectClassifyDialog.setListener(new SelectClassifyDialog.Listener() {
                    @Override
                    public void onSelectClassifyListener(ArticleClassify parentArticleClassify, ArticleClassify childArticleClassify) {
                        updateClassify(parentArticleClassify, childArticleClassify);
                    }
                });
                selectClassifyDialog.show();

            }
        });
        //项目点击
        bindind.projectIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void updateClassify(ArticleClassify parentArticleClassify, ArticleClassify childArticleClassify) {
        ArticleClassify parentClassify = parentArticleClassify;
        ArticleClassify childClassify = childArticleClassify;
        if (parentClassify == null) {
            parentClassify = parentArticleClassifyList.get(0);
        }
        if (childClassify == null) {
            childClassify = parentClassify.getChildren().get(0);
        }
        for (int i = 0; i < parentArticleClassifyList.size(); i++) {
            if (parentArticleClassifyList.get(i).getId() == parentClassify.getId()) {
                parentClassifyPos = i;
                List<ArticleClassify> childArticleList = parentArticleClassifyList.get(i).getChildren();
                for (int j = 0; j < childArticleList.size(); j++) {
                    if (childArticleList.get(j).getId() == childClassify.getId()) {
                        childClassifyPos = j;
                        break;
                    }
                }
                break;
            }
        }
        initTab();
    }

    private void initTab() {
        bindind.tabRl.setVisibility(View.VISIBLE);


        bindind.titleTv.setText(parentArticleClassifyList.get(parentClassifyPos).getName());
        /*bindind.tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });*/
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.clear();
        childArticleClassifyList = parentArticleClassifyList.get(parentClassifyPos).getChildren();
        bindind.tlTab.removeAllTabs();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
