package com.lize.wanandroid.ui.widget.recycler.loadmore.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lize.wanandroid.R;

/**
 * @author Lize
 * on 2019/10/16
 */
public class CommonLoadMoreFooterView extends RelativeLayout implements LoadMoreFooterView {
    private LinearLayout loadingMoreView;
    private LinearLayout loadMoreErrorView;

    public CommonLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public CommonLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.common_load_more_footer, this, true);
        loadingMoreView = findViewById(R.id.ll_loadmore);
        loadMoreErrorView = findViewById(R.id.ll_loadmorefill);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onLoadingMore() {
        loadingMoreView.setVisibility(VISIBLE);
        loadMoreErrorView.setVisibility(GONE);
    }

    @Override
    public void onLoadedError() {
        loadingMoreView.setVisibility(GONE);
        loadMoreErrorView.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadNoMore() {
        loadingMoreView.setVisibility(GONE);
        loadMoreErrorView.setVisibility(GONE);
    }
}
