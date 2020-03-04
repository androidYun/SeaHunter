package com.xhs.baselibrary;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import androidx.multidex.MultiDexApplication;


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 08/04/2019.
 * description:
 */
public class BaseApplication extends MultiDexApplication {
    private static Context sInstance;


    public static Context getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }


}
