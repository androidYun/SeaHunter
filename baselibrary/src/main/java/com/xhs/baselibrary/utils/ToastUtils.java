package com.xhs.baselibrary.utils;

import androidx.annotation.StringRes;
import android.widget.Toast;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 15/04/2019.
 * description:
 */
public class ToastUtils {


    private static Toast mToast;

    public static void show(String content) {
        if (content == null || content.isEmpty()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getsInstance(), content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast = Toast.makeText(BaseApplication.getsInstance(), content, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(@StringRes int resId) {
        String content = UIUtils.getInstance().getString(resId);
        if (content == null || content.isEmpty()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getsInstance(), content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast = Toast.makeText(BaseApplication.getsInstance(), content, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


}
