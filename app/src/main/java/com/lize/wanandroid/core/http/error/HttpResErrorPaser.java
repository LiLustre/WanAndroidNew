package com.lize.wanandroid.core.http.error;

import java.io.IOException;

/**
 * @author lizhen
 */
public class HttpResErrorPaser {

    public static final int UNAUTHORIZED = 401;

    public static final int FORBIDDEN = 403;

    public static final int NOT_FOUND = 404;

    public static final int UNPROCESSABLE_ENTITY = 422;

    /**
     * 解析响应异常
     *
     * @param t
     * @return
     */
    public static String parseResException(Throwable t) {
        if (t instanceof IOException) {
            //return "network error " + t.getMessage();
            return "网络错误";
        } else {
            return "意外错误:" + t.getMessage();
        }
    }

    /**
     * 解析响应错误
     *
     * @param code
     * @param data
     * @param paser
     * @return
     */
    public static String parseResError(int code, String data) {
        if (code == UNAUTHORIZED) {
            return "未授权";
        } else if (code == FORBIDDEN) {
            return "身份认证失败";
        } else if (code == NOT_FOUND) {
            return "请求资源不存在";
        } else if (code == UNPROCESSABLE_ENTITY) {
            return null;
        } else if (code >= 400 && code < 500) {
            return "客户端错误:" + code;
        } else if (code >= 500 && code < 600) {
            return "服务端错误:" + code;
        } else {
            return "意外错误:" + code;
        }
    }


}
