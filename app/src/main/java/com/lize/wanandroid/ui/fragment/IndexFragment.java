package com.lize.wanandroid.ui.fragment;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;

/**
 * @author Lize
 * on 2019/10/15
 */
public class IndexFragment extends BaseFragment {

    private static IndexFragment instance = null;
    public static IndexFragment getInstance(){
        if (instance==null){
            instance = new IndexFragment();
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }
}
