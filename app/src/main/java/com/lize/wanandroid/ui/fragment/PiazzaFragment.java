package com.lize.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentPiazzaBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.model.navi.Navi;
import com.lize.wanandroid.ui.adapter.NaviDetailListAdapter;
import com.lize.wanandroid.ui.adapter.NaviListAdapter;
import com.lize.wanandroid.util.ValueUtil;
import com.lize.wanandroid.viewmodel.ArtcileClassifyViewModel;
import com.lize.wanandroid.viewmodel.NaviClassifyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lize
 * on 2019/10/15
 */
public class PiazzaFragment extends BaseFragment<FragmentPiazzaBinding> {

    private static PiazzaFragment instance = null;
    private NaviClassifyViewModel naviClassifyViewModel;
    private List<Navi> navis;
    private NaviDetailListAdapter naviDetailListAdapter;
    private int parentPos;
    private List<ArticleBean> curNaviDetailList = new ArrayList<>();
    private Navi curNavi;
    private NaviListAdapter naviListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_piazza;
    }

    public static PiazzaFragment getInstance() {
        if (instance == null) {
            instance = new PiazzaFragment();
        }
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindind.setLifecycleOwner(this);
        naviClassifyViewModel = ViewModelProviders.of(this).get(NaviClassifyViewModel.class);
        bindind.refreshLayout.setRefreshing(true);
        naviClassifyViewModel.getNaviListData().observe(this, new Observer<List<Navi>>() {
            @Override
            public void onChanged(List<Navi> articleClassifies) {
                navis = articleClassifies;
                bindind.refreshLayout.setRefreshing(false);
                bindind.refreshLayout.setEnabled(false);
                initAdapter();
            }
        });
        naviClassifyViewModel.getArticleClassifyList();
    }

    private void initAdapter() {
        if (ValueUtil.isListValid(navis)) {
            if (naviListAdapter == null) {
                naviListAdapter = new NaviListAdapter(navis);
                naviListAdapter.setOnClickListener(new NaviListAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        parentPos = pos;
                        curNavi = navis.get(parentPos);
                        curNaviDetailList.clear();
                        curNaviDetailList.addAll(curNavi.getArticleBeans());
                        initChildNaviAdapter();
                    }
                });
                bindind.naviParnetRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                bindind.naviParnetRv.setAdapter(naviListAdapter);
            } else {
                naviListAdapter.notifyDataSetChanged();
            }
            parentPos = 0;
            curNavi = navis.get(parentPos);
            curNaviDetailList.clear();
            curNaviDetailList.addAll(curNavi.getArticleBeans());
            initChildNaviAdapter();
        }

    }

    private void initChildNaviAdapter() {
        if (naviDetailListAdapter == null) {
            naviDetailListAdapter = new NaviDetailListAdapter(curNaviDetailList);
            bindind.naviChildRv.setAdapter(naviDetailListAdapter);
            bindind.naviChildRv.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        } else {
            naviDetailListAdapter.notifyDataSetChanged();
        }
    }
}
