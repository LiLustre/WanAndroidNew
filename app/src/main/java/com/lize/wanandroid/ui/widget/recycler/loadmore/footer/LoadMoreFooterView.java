package com.lize.wanandroid.ui.widget.recycler.loadmore.footer;

import android.view.View;

/**
 * @author Lize
 * on 2019/10/16
 */
public abstract interface LoadMoreFooterView {


    /**
     * 返回分页加载View
     * @return
     */
    public abstract View getView();

    /**
     * 正在加载更多
     */
    public abstract void onLoadingMore();

    /**
     * 加载更多出错
     */
    public abstract void onLoadedError();

    /**
     * 没有下一页
     */
    public abstract void onLoadNoMore();
}
