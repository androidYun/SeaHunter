package com.xhs.baselibrary.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/01/2020.
 * description:
 */
object SoftKeyboardUtil {
    /**
     * 隐藏键盘
     * 弹窗弹出的时候把键盘隐藏掉
     */
     fun hideInputKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


    /**
     * 弹起键盘
     */
     fun showInputKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, 0)
    }

}
