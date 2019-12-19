package com.lize.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.databinding.FragmentMeBinding;
import com.lize.wanandroid.model.login.UserManager;
import com.lize.wanandroid.model.user.UserInfo;
import com.lize.wanandroid.ui.activity.LoginActivity;
import com.lize.wanandroid.ui.activity.SettingActivity;
import com.lize.wanandroid.viewmodel.MeViewModel;

/**
 * @author Lize
 * on 2019/10/15
 */
public class MeFragment extends BaseFragment<FragmentMeBinding> {

    private MeViewModel meViewModel;

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
        bindind.settingIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        meViewModel = ViewModelProviders.of(this).get(MeViewModel.class);
        meViewModel.userLoginStatus.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    bindind.logoutRl.setVisibility(View.VISIBLE);
                } else {
                    bindind.logoutRl.setVisibility(View.GONE);
                }
            }
        });
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
        bindind.userNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().getLoginStatus()) {
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        meViewModel.nickName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bindind.userNameTv.setText(s);
            }
        });
        meViewModel.userInfo.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if (userInfo != null) {
                    bindind.rankingTv.setText(String.valueOf(userInfo.getRank()));
                    bindind.totalGradeTv.setText(String.valueOf(userInfo.getCoinCount()));
                }
            }
        });
        meViewModel.getUserInfo();

        bindind.logoutRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meViewModel.logout();
            }
        });
    }


}
