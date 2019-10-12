package com.lize.wanandroid.util.statusbar;

import android.view.Window;


/**
 * 状态栏接口
 * @author lize
 */
interface IStatusBar {
    void setStatusBarColor(Window window, int color, boolean lightStatusBar);
}

