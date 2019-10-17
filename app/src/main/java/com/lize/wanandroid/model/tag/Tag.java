package com.lize.wanandroid.model.tag;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lize
 * on 2019/10/17
 */
public class Tag {


    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
