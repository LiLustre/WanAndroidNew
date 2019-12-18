package com.lize.wanandroid.model.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {




    @SerializedName("admin")
    private boolean admin;
    @SerializedName("email")
    private String email;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private int id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("password")
    private String password;
    @SerializedName("publicName")
    private String publicName;
    @SerializedName("token")
    private String token;
    @SerializedName("type")
    private int type;
    @SerializedName("username")
    private String username;
    @SerializedName("chapterTops")
    private List<?> chapterTops;
    @SerializedName("collectIds")
    private List<Integer> collectIds;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
