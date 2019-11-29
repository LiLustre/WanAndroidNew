package com.lize.wanandroid.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.ItemChildClassifyBinding;
import com.lize.wanandroid.databinding.ItemParentClassifyBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;

import java.util.List;

/**
 * @author Lize
 * on 2019/10/17
 */
public class ChildClassifyAdapter extends DataBindingRecyclerAdapter {

    private List<ArticleClassify> articleBeans;
    private OnItemClickListener onItemClickListener;

    public ChildClassifyAdapter(List<ArticleClassify> articleBeans) {
        this.articleBeans = articleBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        ItemChildClassifyBinding dataBinding = (ItemChildClassifyBinding) binding;
        dataBinding.tvRightMenu.setText(articleBeans.get(position).getName());
        dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        /*
        //沾满全屏
        ViewGroup.LayoutParams lp = dataBinding.getRoot().getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp =
                    (FlexboxLayoutManager.LayoutParams)dataBinding.getRoot().getLayoutParams();
            flexboxLp.setFlexGrow(1.0f);
            flexboxLp.setAlignSelf(AlignItems.FLEX_END);
        }*/

    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_child_classify;
    }

    @Override
    public int getItemCount() {
        return articleBeans == null ? 0 : articleBeans.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

}
