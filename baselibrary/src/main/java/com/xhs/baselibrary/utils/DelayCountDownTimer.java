package com.xhs.baselibrary.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.SoftReference;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/04/2019.
 * description:
 */
public class DelayCountDownTimer extends CountDownTimer {
    private static final int TIME_TASK = 1000;

    private SoftReference<TextView> viewSoftReference;

    public DelayCountDownTimer(long millisInFuture, TextView tvOperatorView) {
        super(millisInFuture * 1000, TIME_TASK);
        viewSoftReference = new SoftReference(tvOperatorView);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        viewSoftReference.get().setEnabled(false);//防止重复点击
    }

    @Override
    public void onFinish() {
        viewSoftReference.get().setEnabled(true);
    }

    public void cancelTime() {
        this.cancel();
        viewSoftReference.get().setEnabled(true);
    }
}
