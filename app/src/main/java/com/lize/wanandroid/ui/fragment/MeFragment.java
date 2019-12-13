package com.lize.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentMeBinding;

/**
 * @author Lize
 * on 2019/10/15
 */
public class MeFragment extends BaseFragment<FragmentMeBinding> {

    private State state;

    public enum State {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
    private static MeFragment instance = null;

    public static MeFragment getInstance() {
        if (instance == null) {
            instance = new MeFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindind.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != State.EXPANDED) {
                        state = State.EXPANDED;//修改状态标记为展开
                        bindind.titleTv.setVisibility(View.GONE);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != State.COLLAPSED) {
                    //
                        bindind.titleTv.setVisibility(View.VISIBLE);
                        state = State.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != State.INTERNEDIATE) {
                        state = State.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });

    }
}
