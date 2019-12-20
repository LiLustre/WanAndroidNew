package com.lize.wanandroid.base.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.lize.wanandroid.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Lize
 * on 2019/10/15
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    public T bindind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bindind = (T) DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), container, false);
        return bindind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        if (loginEvent!=null){
            if (loginEvent.isLogin()){
                onLogin(loginEvent);
            }else {
               onLogout(loginEvent);
            }
        }
    }

    public void onLogout(LoginEvent loginEvent) {

    }

    public void onLogin(LoginEvent loginEvent) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayoutId();
}
