package com.lize.wanandroid.ui.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Sky on 2017-12-20.
 */
/*
* FragmentStatePagerAdapter比FragmentPagerAdapter更适合用于很多界面之间的转换，而且消耗更少的内存资源
* FragmentPagerAdapter 每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；如果需要处理有很多页，
*   并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter
* FragmentStatePagerAdapte 将只保留当前页面，当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，
* 生成新的页面(就像 ListView 的实现一样)。这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存
* */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
