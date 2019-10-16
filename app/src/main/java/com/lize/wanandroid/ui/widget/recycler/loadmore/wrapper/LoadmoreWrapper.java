package com.lize.wanandroid.ui.widget.recycler.loadmore.wrapper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.lize.wanandroid.ui.widget.recycler.loadmore.footer.CommonLoadMoreFooterView;
import com.lize.wanandroid.ui.widget.recycler.loadmore.footer.LoadMoreFooterView;


/**
 * Created by Sky on 2017-12-20.
 */

public class LoadmoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private LoadMoreFooterView mLoadMoreView;
    private Context context;

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadmoreWrapper(RecyclerView.Adapter adapter, Context context) {
        mInnerAdapter = adapter;
        this.context = context;
        mLoadMoreView = new CommonLoadMoreFooterView(context);

    }


    private boolean hasLoadMore() {
        return mLoadMoreView != null;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            RecyclerView.ViewHolder holder = null;
            if (mLoadMoreView != null) {
                holder = new ViewHolder(mLoadMoreView.getView());
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            loadingMore();
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    //当itemview 被用户看到时，调用该方法
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    public LoadmoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public void loadingMore() {
        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadingMore();
        }
    }

    public void loadMoreError() {
        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadedError();
        }
    }

    public void loadNoMore() {
        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadNoMore();
        }
    }

    public LoadmoreWrapper setLoadMoreView(LoadMoreFooterView loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
