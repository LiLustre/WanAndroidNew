package com.lize.wanandroid.config;

/**
 * @author Lize
 * on 2019/10/16
 */
public class URLConfig {

    private static URLConfig instance;

    public static URLConfig getInstance() {
        if (instance == null) {
            synchronized (URLConfig.class) {
                if (instance == null) {
                    instance = new URLConfig();
                }
            }
        }
        return instance;
    }

}
