package com.lize.wanandroid.ui.fragment;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;

/**
 * @author Lize
 * on 2019/10/15
 */
public class MeFragment extends BaseFragment {

    private static MeFragment instance = null;
    public static MeFragment getInstance(){
        if (instance==null){
            instance = new MeFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }
}
