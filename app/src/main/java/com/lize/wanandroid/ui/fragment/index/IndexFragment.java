package com.lize.wanandroid.ui.fragment.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentIndexBinding;
import com.lize.wanandroid.ui.adapter.base.FragmentAdapter;
import com.lize.wanandroid.ui.fragment.index.child.NewPostsFragment;
import com.lize.wanandroid.ui.fragment.index.child.NewProjectsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class IndexFragment extends BaseFragment<FragmentIndexBinding> {
    private static IndexFragment instance = null;
    private String tabs[] = new String[]{
            "最新博文", "最新项目"
    };
    private List<Fragment> fragments;

    public static IndexFragment getInstance() {
        if (instance == null) {
            instance = new IndexFragment();
        }
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChildFragment();
    }

    private void initChildFragment() {
        for (String tab : tabs) {
            bindind.tlTab.addTab(bindind.tlTab.newTab().setText(tab));
        }
        fragments = new ArrayList<>();
        NewPostsFragment newPostsFragment = NewPostsFragment.getInstance();
        NewProjectsFragment newProjectsFragment = NewProjectsFragment.getInstance();
        fragments.add(newPostsFragment);
        fragments.add(newProjectsFragment);
        bindind.viewpager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        bindind.tlTab.setupWithViewPager(bindind.viewpager);//实现viewpager与tab的联动，但是会使tabLayout的tab不显示，setupWithViewPager方法中移除了tab
        for (int i = 0; i < tabs.length; i++) {//解决tabLayout的tab不显示问题
            bindind.tlTab.getTabAt(i).setText(tabs[i]);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }
}
