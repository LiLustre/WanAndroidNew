package com.lize.wanandroid.config;

import com.lize.wanandroid.BuildConfig;

/**
 * @author Lize
 * on 2019/10/16
 */
public class URLConfig {

    private static URLConfig instance;
    private String serverUrl;

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

    public URLConfig() {
        init();
    }

    private void init() {
        serverUrl = BuildConfig.SERVER_URL;
    }

    public String getServerUrl() {
        return serverUrl;
    }
}
