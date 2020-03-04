package com.xhs.baselibrary.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.ColorInt;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zhf on 2018/10/16 0016.
 */

public class StatusBarUtil {
    public static void setStatusBar(Activity activity, @ColorInt int color){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);
        }

        if(color== Color.WHITE){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
}
