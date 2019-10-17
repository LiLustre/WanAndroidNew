package com.lize.wanandroid.view;

/**
 * @author Lize
 * on 2019/10/16
 */
public interface NewsPostListView extends IBaseView {
    public void onNewPostsPageLoadSuccess();

    public void onNewPostsPageLoadFailed(String msg);

    public void onNewPostsPageLoadError(String msg);


}
