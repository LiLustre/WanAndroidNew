package com.lize.wanandroid.base.checker;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;



public class ActivityChecker {

    /**
     * 检查手机系统设置是否“不保留活动”，用户离开后即销毁每个活动
     * @param context
     * @return
     */
    public static boolean isAlwaysFinishActivities(Context context){
        //0 没有开启；1 开启
        int anInt = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            anInt = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        }
        return anInt == 1;
    }

    /**
     * 检查Activity设置，目前只判断“不保留活动”,如果设置了“不保留活动”，则弹框提醒用户；
     * 点击“取消”，关闭弹框
     * 点击“设置”，进入开发者选项设置或者系统设置
     *
     * @param context
     */
    public static void check(final Context context) {
        if (isAlwaysFinishActivities(context)) {
 /*           CommonPromptDialog commonConfirmDialog = new CommonPromptDialog(context, R.style.Dialog,
                    "因为您已开启“不保留活动”,导致美行停车部分功能无法正常使用.我们建议您点击左下方“设置”按钮,在“开发人员选项”中关闭“不保留活动”功能", "设置", "取消", new CommonPromptDialog.PromptListener() {
                @Override
                public void onPromptCallback() {
                    SystemSettingUtil.startDevelopmentActivity(context);
                }
            });
            commonConfirmDialog.show();*/
        }
    }
}
