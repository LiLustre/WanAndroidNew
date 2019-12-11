package com.lize.wanandroid.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.SelectClassifyDialogLayoutBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.ChildClassifyAdapter;
import com.lize.wanandroid.ui.adapter.ParentClassifyAdapter;
import com.lize.wanandroid.ui.widget.ScrollDownViewLayout;
import com.lize.wanandroid.util.DisplayUtil;
import com.lize.wanandroid.util.ValueUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectClassifyDialog extends Dialog {
    private SelectClassifyDialogLayoutBinding binding;
    private List<ArticleClassify> articleClassifyList;
    private List<ArticleClassify> childArticleClassifyList = new ArrayList<>();
    private ChildClassifyAdapter childClassifyAdapter;
    private ParentClassifyAdapter parentClassifyAdapter;
    private int parentPos = 0;
    private ArticleClassify parentClassify;
    private ArticleClassify childClassify;
    private int childPos = 0;
    private Listener listener;

    public SelectClassifyDialog(Context context, int themeResId, List<ArticleClassify> articleClassifyList) {
        super(context, themeResId);
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.GoUpPopDialogAnim); //设置窗口弹出动画
        this.articleClassifyList = articleClassifyList;
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                binding.root.scrollTo(0, 0);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.select_classify_dialog_layout, null, false);
        setContentView(binding.getRoot());
        initAdapter();

        binding.root.setListener(new ScrollDownViewLayout.Listener() {
            @Override
            public void onDismissListener() {
                dismiss();
            }
        });
    }

    private void initAdapter() {
        if (ValueUtil.isListValid(articleClassifyList)) {
            binding.tvTypeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                       listener.onSelectClassifyListener(articleClassifyList.get(parentPos),null);
                       dismiss();
                    }
                }
            });
        }
        parentClassifyAdapter = new ParentClassifyAdapter(articleClassifyList);
        parentClassifyAdapter.setOnItemClickListener(new ParentClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                parentPos = pos;
                childPos = 0;
                parentClassifyAdapter.setSelectedPos(pos);
                parentClassifyAdapter.notifyDataSetChanged();

                initChildClassifyAdapter(parentPos);
            }
        });
        binding.articleParentClsssifyRv.setAdapter(parentClassifyAdapter);
        binding.articleParentClsssifyRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        initChildClassifyAdapter(parentPos);

    }

    private void initChildClassifyAdapter(int pos) {
        parentClassify = articleClassifyList.get(pos);
        childArticleClassifyList.clear();
        childArticleClassifyList.addAll(parentClassify.getChildren());
        childClassify = childArticleClassifyList.get(childPos);
        binding.tvTypeName.setText(parentClassify.getName());
        if (childClassifyAdapter == null) {
            childClassifyAdapter = new ChildClassifyAdapter(childArticleClassifyList);
            childClassifyAdapter.setOnItemClickListener(new ChildClassifyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    childPos = pos;
                    childClassify = childArticleClassifyList.get(childPos);
                    if (listener != null) {
                        listener.onSelectClassifyListener(articleClassifyList.get(parentPos),childArticleClassifyList.get(childPos));
                    }
                    dismiss();
                }
            });
            binding.articleChildClsssifyRv.setAdapter(childClassifyAdapter);

            //设置布局管理器
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager();
            //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
            //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
            //justifyContent 属性定义了项目在主轴上的对齐方式。
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
            binding.articleChildClsssifyRv.setLayoutManager(flexboxLayoutManager);
        } else {
            childClassifyAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = DisplayUtil.getScreenHeight(getContext()) - DisplayUtil.getStatusBarHeight(getContext()) - DisplayUtil.dip2px(getContext(), 50);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface Listener {
        void onSelectClassifyListener(ArticleClassify parentArticleClassify, ArticleClassify childArticleClassify);
    }
}
