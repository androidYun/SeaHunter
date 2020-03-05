package com.xhs.baselibrary.utils

import android.graphics.Color
import android.os.CountDownTimer
import android.widget.TextView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/03/2020.
 * description:
 */
class TimeCountDown(private val textView: TextView, millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {


    override fun onFinish() {
        textView?.let {
            textView.text = "重新获取验证码"
            textView.isClickable = true
            textView.setBackgroundColor(Color.parseColor("#4EB84A"))
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        textView?.let {
            textView.setBackgroundColor(Color.parseColor("#B6B6D8"))
            textView.isClickable = false
            textView.text = "(" + millisUntilFinished / 1000 + ") 秒后可重新发送"
        }
    }
}