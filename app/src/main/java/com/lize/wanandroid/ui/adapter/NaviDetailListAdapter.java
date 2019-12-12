package com.lize.wanandroid.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.NaviDetailLayoutBinding;
import com.lize.wanandroid.model.article.ArticleBean;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;
import com.lize.wanandroid.util.ColorUtil;

import java.util.List;

public class NaviDetailListAdapter extends DataBindingRecyclerAdapter {

    private List<ArticleBean> articleBeans;
    private OnClickListener onClickListener;
    public NaviDetailListAdapter(List<ArticleBean> articleBeans) {
        this.articleBeans = articleBeans;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        NaviDetailLayoutBinding navibinding = (NaviDetailLayoutBinding) binding;
        ArticleBean articleBean = articleBeans.get(position);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.navi_bg);
        drawable.setColor(ColorUtil.randomColor());
        binding.getRoot().setBackground(drawable);
        navibinding.detailNameTv.setText(articleBean.getTitle());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null){
                    onClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.navi_detail_layout;
    }

    @Override
    public int getItemCount() {
        return articleBeans == null ? 0 : articleBeans.size();
    }

    public interface OnClickListener{
        void onItemClick(int pos);
    }
}
