package com.lize.wanandroid.ui.fragment.index;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentIndexBinding;
import com.lize.wanandroid.model.login.UserManager;
import com.lize.wanandroid.ui.activity.LoginActivity;
import com.lize.wanandroid.ui.activity.SearchActivity;
import com.lize.wanandroid.ui.activity.ToDoActivity;
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
        instance = new IndexFragment();
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChildFragment();
        bindind.searchBarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        bindind.waitDoIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().getLoginStatus()) {
                    Intent intent = new Intent(getActivity(), ToDoActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
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


}
