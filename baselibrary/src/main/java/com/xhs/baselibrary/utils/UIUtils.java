package com.xhs.baselibrary.utils;

import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ email xyz_6776.@163.com
 * @ date 02/04/2019.
 * description:
 */
public class UIUtils {

    private static UIUtils uiUtils;

    static final class UIUtilsHolder {
        final static UIUtils UI_UTILS = new UIUtils();
    }

    public static UIUtils getInstance() {
        if (uiUtils == null) {
            uiUtils = UIUtilsHolder.UI_UTILS;
        }
        return uiUtils;
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return BaseApplication.getsInstance().getString(resId, formatArgs);
    }


    public int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(BaseApplication.getsInstance(), resId);
    }

    public Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(BaseApplication.getsInstance(), resId);
    }
}
