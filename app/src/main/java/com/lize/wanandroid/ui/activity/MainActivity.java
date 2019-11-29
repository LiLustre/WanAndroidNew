package com.lize.wanandroid.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityMainBinding;
import com.lize.wanandroid.domain.StartActivityForDomain;
import com.lize.wanandroid.ui.fragment.classify.ClassifyFragment;
import com.lize.wanandroid.ui.fragment.MeFragment;
import com.lize.wanandroid.ui.fragment.PiazzaFragment;
import com.lize.wanandroid.ui.fragment.index.IndexFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static final String SELECT_ITEM_POS = "bottomNavigationSelectItem";
    private static final int HOME_SELECT_ITEM_POS = 0;
    private static final int CLASSIFY_SELECT_ITEM_POS = 1;
    private static final int SQUARESELECT_ITEM_POS = 2;
    private static final int ME_SELECT_ITEM_POS = 3;
    private ClassifyFragment classifyFragment;
    private IndexFragment indexFragment;
    private PiazzaFragment piazzaFragment;
    private MeFragment meFragment;
    private int selectedBottomPos;

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
            showFragment(savedInstanceState.getInt(SELECT_ITEM_POS));
        } else {
            showFragment(HOME_SELECT_ITEM_POS);
        }
        // initBottomTab();
    }

    public void onHomeClick(View view) {
        showFragment(HOME_SELECT_ITEM_POS);
    }

    public void onClassifyClick(View view) {
        showFragment(CLASSIFY_SELECT_ITEM_POS);
    }

    public void onSquareClick(View view) {
        showFragment(SQUARESELECT_ITEM_POS);
    }

    public void onMeClick(View view) {
        showFragment(ME_SELECT_ITEM_POS);
    }


    private void checkHomeBtn() {
        binding.homeRb.setSelected(true);
        binding.meRb.setSelected(false);
        binding.classifyRb.setSelected(false);
        binding.squareRb.setSelected(false);
    }

    private void checkClassifyBtn() {
        binding.homeRb.setSelected(false);
        binding.classifyRb.setSelected(true);
        binding.squareRb.setSelected(false);
        binding.meRb.setSelected(false);
    }

    private void checkSquareBtn() {
        binding.homeRb.setSelected(false);
        binding.meRb.setSelected(false);
        binding.classifyRb.setSelected(false);
        binding.squareRb.setSelected(true);
    }

    private void checkMeBtn() {
        binding.homeRb.setSelected(false);
        binding.meRb.setSelected(true);
        binding.classifyRb.setSelected(false);
        binding.squareRb.setSelected(false);
    }


    private void showFragment(@NonNull int itemPos) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        selectedBottomPos = itemPos;
        switch (selectedBottomPos) {
            case HOME_SELECT_ITEM_POS:
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
                checkHomeBtn();
                break;
            case CLASSIFY_SELECT_ITEM_POS:
                if (classifyFragment == null) {
                    classifyFragment = ClassifyFragment.getInstance();
                    ft.add(R.id.content_fl, classifyFragment, ClassifyFragment.class.getName());
                } else {
                    ft.show(classifyFragment);
                }
                checkClassifyBtn();
                break;
            case SQUARESELECT_ITEM_POS:
                if (piazzaFragment == null) {
                    piazzaFragment = PiazzaFragment.getInstance();
                    ft.add(R.id.content_fl, piazzaFragment, PiazzaFragment.class.getName());
                } else {
                    ft.show(piazzaFragment);
                }
                checkSquareBtn();
                break;
            case ME_SELECT_ITEM_POS:
                if (meFragment == null) {

                    meFragment = MeFragment.getInstance();
                    ft.add(R.id.content_fl, meFragment, IndexFragment.class.getName());
                } else {
                    ft.show(meFragment);
                }
                checkMeBtn();
                break;
        }
        ft.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // recreate 时记录当前位置 (在 Manifest 已禁止 Activity 旋转,所以旋转屏幕并不会执行以下代码)
        super.onSaveInstanceState(outState);
        outState.putInt(SELECT_ITEM_POS, selectedBottomPos);
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
