package com.lize.wanandroid.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by 71019 on 2018/4/26.
 */

public class CacheUtil {

    public static long getTotalCacheSize(Context context){
        try {
            long total = 0;
            long extCacheSize = 0;
            File cacheFile =  context.getCacheDir();
            Log.d("缓存目录",cacheFile.getAbsolutePath());

            long cacheSize = getFolderSize(cacheFile);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File extCacheFile =  context.getExternalCacheDir();
                 extCacheSize = getFolderSize(extCacheFile);
            }
           return extCacheSize+cacheSize;
        }catch (Exception e){
            Log.e("getTotalCacheSize","获取缓存大小异常");
        }
        return 0;
    }

    public static void clearAllCache(Context context){
        deleateDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            deleateDir(context.getExternalCacheDir());
        }
    }

    public static void deleateDir(File dir){
        if (dir!=null&& dir.exists()&&dir.isDirectory()){
            File [] files = dir.listFiles();
            for (File file:files){
                if (file.isDirectory()){
                    deleateDir(file);
                }else {
                    file.delete();
                }

            }

        }
    }

    public static String getCacheSize(Context context){
        return getFormatSize(getTotalCacheSize(context));
    }

    private static long getFolderSize(File cacheFile) {
        long size = 0;

        File []files = cacheFile.listFiles();
        //获取子文件
        if (files!=null){
            for (File file:files){
                if (file.isDirectory()){
                    size = size + getFolderSize(file);
                }else {
                    //
                    size = size+file.length();
                }
            }
        }
        return size;

    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;


        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
