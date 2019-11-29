package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityArticleClassifyBinding;
import com.lize.wanandroid.model.classify.ArticleClassify;
import com.lize.wanandroid.ui.adapter.ChildClassifyAdapter;
import com.lize.wanandroid.ui.adapter.ParentClassifyAdapter;
import com.lize.wanandroid.util.ValueUtil;

import java.util.ArrayList;
import java.util.List;

public class ArticleClassifyActivity extends BaseActivity<ActivityArticleClassifyBinding> {
    private List<ArticleClassify> articleClassifyList;
    private List<ArticleClassify> childArticleClassifyList = new ArrayList<>();
    private ChildClassifyAdapter childClassifyAdapter;
    private ParentClassifyAdapter parentClassifyAdapter;
    private int parentPos = 0;
    private ArticleClassify parentClassify;
    private ArticleClassify childClassify;
    private int childPos = 0;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        articleClassifyList = getIntent().getParcelableArrayListExtra("classify");

        if (ValueUtil.isListValid(articleClassifyList)) {
            initAdapter();
        }
    }

    private void initAdapter() {
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
        binding.articleParentClsssifyRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
                    backOnResult();
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
    public int getLayoutId() {
        return R.layout.activity_article_classify;
    }

    public void onBackClick(View view) {
        finish();
    }

    public void onParentClassifyClick(View view) {
        backOnResult();
    }

    private void backOnResult() {
        Intent intent = new Intent();
        intent.putExtra("parentClassify", parentClassify);
        intent.putExtra("childClassify", childClassify);
        setResult(RESULT_OK, intent);
        finish();
    }
}
