package com.lize.wanandroid.ui.fragment;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;

/**
 * @author Lize
 * on 2019/10/15
 */
public class ClassifyFragment extends BaseFragment {
    private static ClassifyFragment instance = null;
    public static ClassifyFragment getInstance(){
        if (instance==null){
            instance = new ClassifyFragment();
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }
}
