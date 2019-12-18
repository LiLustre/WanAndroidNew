package com.lize.wanandroid.model.user;

import com.google.gson.annotations.*;

public class UserInfo {


    /**
     * 总积分
     */
    @SerializedName("coinCount")
    private int coinCount;
    /**
     * 等级 level
     */
    @SerializedName("level")
    private int level;
    /**
     * 当前排名
     */
    @SerializedName("rank")
    private int rank;
    @SerializedName("userId")
    private int userId;
    @SerializedName("username")
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
