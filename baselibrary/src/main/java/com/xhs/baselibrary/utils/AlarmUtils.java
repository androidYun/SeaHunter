package com.xhs.baselibrary.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 25/12/2019.
 * description:
 */
public class AlarmUtils {
    private static final AlarmUtils ourInstance = new AlarmUtils();

    public static AlarmUtils getInstance() {
        return ourInstance;
    }

    private AlarmUtils() {
    }

    PendingIntent pi;

    public void startAlarm(Context context, Intent intent, long intervalMillis) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // 创建PendingIntent对象
        if (pi == null) {
            pi = PendingIntent.getBroadcast(
                    context, 0, intent, 0);
        }
        // 设置AlarmManager将在Calendar对应的时间启动指定组件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalMillis, pi);
        }
    }

    public void cancelAlarm(Context context, Intent intent) {
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(sender);
    }

    public boolean isRegister(String action) {
        return (PendingIntent.getBroadcast(BaseApplication.getsInstance(), 0,
                new Intent(action),
                PendingIntent.FLAG_NO_CREATE) != null);
    }
}
