package com.lize.wanandroid.ui.adapter;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.SecondaryArticleClassifyItemBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/17
 */
public class SecondaryArticleClassifyAdapter extends DataBindingRecyclerAdapter {

    private List<ArticleClassify> articleClassifyList;
    private OnItemClickListener onItemClickListener;
    private int selectPos;

    public SecondaryArticleClassifyAdapter(List<ArticleClassify> articleClassifyList) {
        this.articleClassifyList = articleClassifyList;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        SecondaryArticleClassifyItemBinding dataBinding = (SecondaryArticleClassifyItemBinding) binding;
        dataBinding.classifyNameTv.setText(articleClassifyList.get(position).getName());
        dataBinding.classifyNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SecondaryArticleClassifyAdapter.this.onItemClickListener != null) {
                    selectPos = position;
                    notifyDataSetChanged();
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        if (selectPos == position) {
            dataBinding.classifyNameTv.setBackground(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.secondary_classify_selected_bg));
            dataBinding.classifyNameTv.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white));
        } else {
            dataBinding.classifyNameTv.setBackground(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.secondary_classify_normal_bg));
            dataBinding.classifyNameTv.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.secondary_classify_normal_text_color));
        }
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.secondary_article_classify_item;
    }

    @Override
    public int getItemCount() {
        return articleClassifyList == null ? 0 : articleClassifyList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

}
