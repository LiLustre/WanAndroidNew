package com.lize.wanandroid.util.statusbar;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;


import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;


/**
 * 设置状态栏颜色
 * @author lize
 */
public class StatusBarCompat {

    static final IStatusBar IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarKitkatImpl();
        } else {
            IMPL = new IStatusBar() {
                @Override
                public void setStatusBarColor(Window window, int color, boolean lightStatusBar) {
                }
            };
        }
    }


    /**
     * Set system status bar color.
     *
     * @param activity
     * @param color          status bar color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0) {
            return;
        }
        IMPL.setStatusBarColor(window, color, !isDarkColor(color));
    }

    /**
     * Set system status bar color.
     *
     * @param activity
     * @param color          status bar color
     * @param lightStatusBar if the status bar color is light. Only effective when API >= 23
     */
    public static void setStatusBarColor(Activity activity, int color, boolean lightStatusBar) {
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0) {
            return;
        }
        IMPL.setStatusBarColor(window, color, lightStatusBar);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void setFitsSystemWindows(Window window, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(fitSystemWindows);
            }
        }
    }

    /**
     * 判断颜色是否为深色
     *
     * @param color 要判断的颜色
     * @return 是否为深色
     */
    public static boolean isDarkColor(@ColorInt int color) {
//        double darkness =
//                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
//        return darkness >= 0.5;
        return ColorUtils.calculateLuminance(color) < 0.5;
    }
}
