package com.lize.wanandroid.model.search;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;

    private String key;

    private long createTime;

    @Generated(hash = 1068277959)
    public SearchHistory(Long id, String key, long createTime) {
        this.id = id;
        this.key = key;
        this.createTime = createTime;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

  
}
