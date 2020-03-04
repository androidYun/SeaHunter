package com.xhs.baselibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 11/04/2019.
 * description:
 */
public class ScreenUtils {

    private static ScreenUtils sInstance;

    public static ScreenUtils getInstance() {
        if (sInstance == null) {
            sInstance = new ScreenUtils();
        }
        return sInstance;
    }

    /**
     * 获取屏幕的宽
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseApplication.getsInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) BaseApplication.getsInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
