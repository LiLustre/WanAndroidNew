package com.lize.wanandroid.util;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {

        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        //取70到190 之间的随机数，放置颜色过深
        // random.nextInt(max) % (max - min + 1) + min
        int red = random.nextInt(190) % (190 - 70 + 1) + 70;
        int green = random.nextInt(190) % (190 - 70 + 1) + 70;
        int blue = random.nextInt(190) % (190 - 70 + 1) + 70;
       /* if (SettingUtil.getIsNightMode()) {
            //150-255
            red = random.nextInt(105) + 150;
            green = random.nextInt(105) + 150;
            blue = random.nextInt(105) + 150;
        }*/
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

}
