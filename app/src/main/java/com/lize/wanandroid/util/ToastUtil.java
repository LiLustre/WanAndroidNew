/**
 *
 */
package com.lize.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;
    private static long mLastTime = 0L;
    /**
     * Toast属性
     */
    private static String mLastStr = "";

    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;// toast隐藏后，将其置为null
            }
        }
    };


    /**
     * Toast
     *
     * @param context
     * @param sToast
     */
    public static void showToast(final Context context, final String sToast) {
        if (null == sToast || "".equals(sToast)) {
            return;
        }
        if (System.currentTimeMillis() - mLastTime < 3000) {
            if (sToast.equals(mLastStr)) {
                return;
            }
        }
        mLastStr = sToast;
        mLastTime = System.currentTimeMillis();
        try {
            if (context != null) {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context.getApplicationContext(), sToast, Toast.LENGTH_SHORT);
                        toast.setText(sToast);
                        toast.show();
                    }
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void show(Context context, String info) {
        showToast(context, info);
    }

    public static void show(Context context, int info) {
        showToast(context,context.getResources().getString(info));
    }

}
