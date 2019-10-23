package com.lize.wanandroid.ui.adapter;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.ArticleRecylerItemBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.model.tag.Tag;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;
import com.lize.wanandroid.util.ValueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lize
 * on 2019/10/17
 */
public class ArticleListAdapter extends DataBindingRecyclerAdapter {

    private List<ArticleBean> articleBeans;
    private OnItemClickListener onItemClickListener;

    public ArticleListAdapter(List<ArticleBean> articleBeans) {
        this.articleBeans = articleBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, DataBindingRecyclerAdapter.VH holder) {
        ArticleRecylerItemBinding dataBinding = (ArticleRecylerItemBinding) binding;
        ArticleBean articleBean = articleBeans.get(position);
        List<String> flag = new ArrayList<>();
        if (articleBean.isTop()) {
            dataBinding.topIv.setVisibility(View.VISIBLE);
        } else {
            dataBinding.topIv.setVisibility(View.GONE);
        }
        if (articleBean.isFresh()) {
            flag.add("最近收录");
        }
        if (ValueUtil.isListValid(flag)) {
            dataBinding.titleTv.setContentAndTag("  " + articleBean.getTitle(), flag);
        } else {
            dataBinding.titleTv.setText(articleBean.getTitle());
        }

        List<String> tag = new ArrayList<>();
        if (ValueUtil.isListValid(articleBean.getTags())) {
            for (Tag tag1 : articleBean.getTags()) {
                tag.add(tag1.getName());
            }
        }
        if (ValueUtil.isListValid(tag)) {
            dataBinding.classifyTv.setContentAndTag("  " + articleBean.getSuperChapterName() + " / " + articleBean.getChapterName(), tag);
        } else {
            dataBinding.classifyTv.setText(articleBean.getSuperChapterName() + " / " + articleBean.getChapterName());
        }

        dataBinding.timeTv.setText(articleBean.getNiceDate());
        if (ValueUtil.isStringValid(articleBean.getEnvelopePic())) {
            Glide.with(binding.getRoot().getContext())
                    .load(articleBean.getEnvelopePic())
                    .crossFade(500)
                    .into(dataBinding.articleImgIv);
        } else {
            dataBinding.articleImgIv.setVisibility(View.GONE);
        }
        dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ArticleListAdapter.this.onItemClickListener != null) {
                    ArticleListAdapter.this.onItemClickListener.onItemClick(position);
                }
            }
        });
        if (ValueUtil.isStringValid(articleBean.getShareUser())) {
            dataBinding.authorTv.setText("分享•" + articleBean.getShareUser());
        }
        if (ValueUtil.isStringValid(articleBean.getAuthor())) {
            dataBinding.authorTv.setText("原创•" + articleBean.getAuthor());
        }
        dataBinding.moreIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ArticleListAdapter.this.onItemClickListener != null) {
                    ArticleListAdapter.this.onItemClickListener.onMoreClick(position);
                }
            }
        });
        if (ValueUtil.isStringValid(articleBean.getDesc())) {
            dataBinding.descTv.setVisibility(View.VISIBLE);
            dataBinding.descTv.setText(articleBean.getDesc());
        } else {
            dataBinding.descTv.setVisibility(View.GONE);
        }
        dataBinding.collectionIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ArticleListAdapter.this.onItemClickListener != null) {
                    ArticleListAdapter.this.onItemClickListener.onCollectionClick(position);
                }
            }
        });
        if (articleBean.isCollect()) {
            ((ArticleRecylerItemBinding) binding).collectionIb.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.is_collection_icon));
        } else {
            ((ArticleRecylerItemBinding) binding).collectionIb.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.collection_icon));

        }
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.article_recyler_item;
    }

    @Override
    public int getItemCount() {
        return articleBeans == null ? 0 : articleBeans.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);

        void onMoreClick(int pos);

        void onCollectionClick(int pos);
    }

}
