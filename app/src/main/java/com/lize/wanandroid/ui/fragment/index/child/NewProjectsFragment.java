package com.lize.wanandroid.ui.fragment.index.child;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.BaseFragment;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentNewProjectsBinding;

/**
 * @author Lize
 * on 2019/10/15
 */
public class NewProjectsFragment extends LazyBaseFragment<FragmentNewProjectsBinding> {
    private static NewProjectsFragment instance = null;
    public static NewProjectsFragment getInstance(){
        if (instance==null){
            instance = new NewProjectsFragment();
        }
        return instance;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_projects;
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }
}
