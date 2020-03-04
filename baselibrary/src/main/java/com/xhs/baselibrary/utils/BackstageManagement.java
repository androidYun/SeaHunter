package com.xhs.baselibrary.utils;

import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 24/12/2019.
 * description:
 */
public class BackstageManagement {
    private static final BackstageManagement ourInstance = new BackstageManagement();

    public static BackstageManagement getInstance() {
        return ourInstance;
    }

//    /**
//     * 跳转到指定应用的首页
//     */
//    private void showActivity(@NonNull String packageName) {
//        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
//        startActivity(intent);
//    }

    /**
     * 跳转到指定应用的指定页面
     */
    private void showActivity(@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getsInstance().startActivity(intent);
    }

    private void goHuaweiSetting() {
        try {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }

    }

    public void toSeting() {
        if (SystemUtils.isHuaWei()) {
            goHuaweiSetting();
        }
    }
}
