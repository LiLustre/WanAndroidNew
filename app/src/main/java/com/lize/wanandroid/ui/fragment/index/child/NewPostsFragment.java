package com.lize.wanandroid.ui.fragment.index.child;



import androidx.lifecycle.ViewModelProvider;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.fragment.LazyBaseFragment;
import com.lize.wanandroid.databinding.FragmentNewPostsBinding;
import com.lize.wanandroid.viewmodel.NewPostsViewModel;

/**
 * @author Lize
 * on 2019/10/15
 */
public class NewPostsFragment extends LazyBaseFragment<FragmentNewPostsBinding> {
    private static NewPostsFragment instance = null;

    public static NewPostsFragment getInstance() {
        if (instance == null) {
            instance = new NewPostsFragment();
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_posts;
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        bindind.setLifecycleOwner(this);
        ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(NewPostsViewModel.class);
    }
}
