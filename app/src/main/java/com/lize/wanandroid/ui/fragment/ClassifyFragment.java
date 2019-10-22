package com.lize.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentClassifyBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.viewmodel.ArtcileClassifyViewModel;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding> {
    private static ClassifyFragment instance = null;
    private ArtcileClassifyViewModel artcileClassifyViewModel;

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
               initTab(articleClassifies);
            }
        });
    }

    private void initTab(List<ArticleClassify> articleClassifies) {

        for (ArticleClassify tab : articleClassifies) {
            bindind.tlTab.addTab(bindind.tlTab.newTab().setText(tab.getName()));
        }
    }
}
