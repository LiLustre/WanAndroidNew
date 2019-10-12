package com.lize.wanandroid.base.checker;

/**
 * @author lizhen
 */
public class ApplicationChecker {

    /**
     * 应用是否启动
     */
    private static boolean applicationStarted;

    /**
     * 启动页是否启动
     */
    private static boolean bootStarted;

    /**
     * 主页是否启动
     */
    private static boolean mainStarted;

    /**
     * 标记应用已启动
     */
    public static void markApplicationCreated() {
        applicationStarted = true;
    }

    /**
     * 标记启动页已启动
     */
    public static void markBootActivityCreated() {
        bootStarted = true;
    }

    /**
     * 标记主页已启动
     */
    public static void markMainActivityCreated() {
        mainStarted = true;
    }

    /**
     * 启动页是否初始化完成
     *
     * @return
     */
    public static boolean bootStarted() {
        return applicationStarted && bootStarted;
    }

    /**
     * 主页是否初始化完成
     *
     * @return
     */
    public static boolean mainStarted() {
        return applicationStarted && bootStarted && mainStarted;
    }
}
