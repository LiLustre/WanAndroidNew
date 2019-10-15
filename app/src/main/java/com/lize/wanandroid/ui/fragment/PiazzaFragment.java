package com.lize.wanandroid.ui.fragment;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;

/**
 * @author Lize
 * on 2019/10/15
 */
public class PiazzaFragment extends BaseFragment {

    private static PiazzaFragment instance = null;

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


}
