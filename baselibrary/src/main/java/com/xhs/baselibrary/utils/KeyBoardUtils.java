package com.xhs.baselibrary.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 18/05/2019.
 * description:
 */
public class KeyBoardUtils {

    /**
     * 强制隐藏键盘
     *
     * @param context
     */
    public static void hideKeyBoard(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
