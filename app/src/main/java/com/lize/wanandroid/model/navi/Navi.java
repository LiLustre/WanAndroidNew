package com.lize.wanandroid.model.navi;

import com.google.gson.annotations.SerializedName;
import com.lize.wanandroid.model.article.ArticleBean;

import java.util.List;

public class Navi {
    @SerializedName("cid")
    private int cid;
    @SerializedName("name")
    private String name;

    @SerializedName("articles")
    private List<ArticleBean> articleBeans;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleBean> getArticleBeans() {
        return articleBeans;
    }

    public void setArticleBeans(List<ArticleBean> articleBeans) {
        this.articleBeans = articleBeans;
    }
}
