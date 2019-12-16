package com.lize.wanandroid.model.search;

import com.lize.wanandroid.base.BaseApplication;
import com.lize.wanandroid.util.ValueUtil;

import java.util.List;

public class SearchHistoryModel {

    public List<SearchHistory> getSearchList(DaoSession daoSession) {
        return daoSession.getSearchHistoryDao().queryBuilder().orderDesc(SearchHistoryDao.Properties.CreateTime).limit(20).build().list();
    }

    public void clearAllSearchHistory(DaoSession daoSession) {
        daoSession.getSearchHistoryDao().deleteAll();
    }


    public void addSearchHistory(SearchHistory searchHistory, DaoSession daoSession) {
        List<SearchHistory> curSearchHistories = daoSession.getSearchHistoryDao().queryBuilder().where(SearchHistoryDao.Properties.Key.eq(searchHistory.getKey())).build().list();
        if (!ValueUtil.isListValid(curSearchHistories)){
            daoSession.getSearchHistoryDao().insert(searchHistory);
            long curCount = daoSession.getSearchHistoryDao().queryBuilder().count();
            if (curCount > 20) {
                List<SearchHistory> searchHistories = daoSession.getSearchHistoryDao().queryBuilder().orderAsc(SearchHistoryDao.Properties.CreateTime).list();
                daoSession.getSearchHistoryDao().delete(searchHistories.get(0));
            }
        }

    }

}
