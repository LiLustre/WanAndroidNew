package com.lize.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SystemKeyBroadUtil {

    /**
     * 关闭某个view弹出的输入法
     */
    public static void hideKeyBoard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void showKeyBoard(final Context context, final View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);
    }

}
