package com.lize.wanandroid.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityMainBinding;
import com.lize.wanandroid.ui.fragment.ClassifyFragment;
import com.lize.wanandroid.ui.fragment.index.IndexFragment;
import com.lize.wanandroid.ui.fragment.MeFragment;
import com.lize.wanandroid.ui.fragment.PiazzaFragment;
import com.lize.wanandroid.ui.helper.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static final String SELECT_ITEM = "bottomNavigationSelectItem";
    private ClassifyFragment classifyFragment;
    private IndexFragment indexFragment;
    private PiazzaFragment piazzaFragment;
    private MeFragment meFragment;
    private int selectedBottomId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            classifyFragment = (ClassifyFragment) getSupportFragmentManager().findFragmentByTag(ClassifyFragment.class.getName());
            indexFragment = (IndexFragment) getSupportFragmentManager().findFragmentByTag(IndexFragment.class.getName());
            piazzaFragment = (PiazzaFragment) getSupportFragmentManager().findFragmentByTag(PiazzaFragment.class.getName());
            meFragment = (MeFragment) getSupportFragmentManager().findFragmentByTag(MeFragment.class.getName());
            // 恢复 recreate 前的位置
            if (savedInstanceState.getInt(SELECT_ITEM) == 0) {
                binding.bnvBar.setSelectedItemId(R.id.menu_item_index);
                showFragment(R.id.menu_item_index);
            } else {
                binding.bnvBar.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
                showFragment(savedInstanceState.getInt(SELECT_ITEM));
            }
        } else {
            showFragment(R.id.menu_item_index);
        }
        initBottomTab();
    }

    private void initBottomTab() {
        BottomNavigationViewHelper.disableShiftMode(binding.bnvBar);
        binding.bnvBar.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
    }

    private void showFragment(@NonNull int itemId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);

        if (itemId == 0) {
            selectedBottomId = R.id.menu_item_index;
        } else {
            selectedBottomId = itemId;
        }
        switch (selectedBottomId) {
            case R.id.menu_item_index:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (indexFragment == null) {
                    indexFragment = IndexFragment.getInstance();
                    ft.add(R.id.content_fl, indexFragment, IndexFragment.class.getName());
                } else {
                    ft.show(indexFragment);
                }
                break;
            case R.id.menu_item_classify:
                if (classifyFragment == null) {
                    classifyFragment = ClassifyFragment.getInstance();
                    ft.add(R.id.content_fl, classifyFragment, ClassifyFragment.class.getName());
                } else {
                    ft.show(classifyFragment);
                }
                break;
            case R.id.menu_item_piazza:
                if (piazzaFragment == null) {
                    piazzaFragment = PiazzaFragment.getInstance();
                    ft.add(R.id.content_fl, piazzaFragment, PiazzaFragment.class.getName());
                } else {
                    ft.show(piazzaFragment);
                }
                break;
            case R.id.menu_item_me:
                if (meFragment == null) {

                    meFragment = MeFragment.getInstance();
                    ft.add(R.id.content_fl, meFragment, IndexFragment.class.getName());
                } else {
                    ft.show(meFragment);
                }
                break;
        }
        ft.commit();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // recreate 时记录当前位置 (在 Manifest 已禁止 Activity 旋转,所以旋转屏幕并不会执行以下代码)
        super.onSaveInstanceState(outState);
        outState.putInt(SELECT_ITEM, binding.bnvBar.getSelectedItemId());
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (indexFragment != null) {
            ft.hide(indexFragment);
        }
        if (classifyFragment != null) {
            ft.hide(classifyFragment);
        }
        if (piazzaFragment != null) {
            ft.hide(piazzaFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
    }
}
