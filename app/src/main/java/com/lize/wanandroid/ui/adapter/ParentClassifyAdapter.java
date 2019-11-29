package com.lize.wanandroid.ui.adapter;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.ArticleRecylerItemBinding;
import com.lize.wanandroid.databinding.ItemParentClassifyBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.model.tag.Tag;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;
import com.lize.wanandroid.util.ValueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lize
 * on 2019/10/17
 */
public class ParentClassifyAdapter extends DataBindingRecyclerAdapter {

    private List<ArticleClassify> articleBeans;
    private OnItemClickListener onItemClickListener;
    private int selectedPos;

    public ParentClassifyAdapter(List<ArticleClassify> articleBeans) {
        this.articleBeans = articleBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    @Override
    protected void onBindView(final ViewDataBinding binding, final int position, VH holder) {
        final ItemParentClassifyBinding dataBinding = (ItemParentClassifyBinding) binding;
        dataBinding.tvItemTitle.setText(articleBeans.get(position).getName());
        dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ParentClassifyAdapter.this.onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        if (selectedPos == position) {
            dataBinding.getRoot().setBackgroundColor(ContextCompat.getColor(dataBinding.getRoot().getContext(), R.color.white));
        } else {
            dataBinding.getRoot().setBackgroundColor(ContextCompat.getColor(dataBinding.getRoot().getContext(), R.color.gray_300));
        }
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_parent_classify;
    }

    @Override
    public int getItemCount() {
        return articleBeans == null ? 0 : articleBeans.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);

    }

}
