package com.xhs.baselibrary.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.core.content.FileProvider;
import android.text.TextUtils;

import com.xhs.baselibrary.BaseApplication;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/05/2019.
 * description:
 */
public class SystemUtils {
    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSdkVersion() {
        return String.valueOf(android.os.Build.VERSION.SDK_INT);
    }

    /*打开自启动管理页*/
    public static void openStart(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        Intent intent = new Intent();
        if (isHuaWei()) {//华为
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            intent.setComponent(componentName);
        } else if (isXiaoMi()) {//小米
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            intent.setComponent(componentName);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }

    public static final String SYS_MIUI = "sys_miui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";


    public static boolean isHuaWei() {
        if (!TextUtils.isEmpty(getSystemProperty(KEY_EMUI_API_LEVEL, ""))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_VERSION, ""))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, ""))) {
            return true;
        }
        return false;
    }

    public static boolean isXiaoMi() {
        if (!TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_CODE, ""))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_NAME, ""))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_INTERNAL_STORAGE, ""))
        ) {
            return true;
        }
        return false;
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == android.os.Process.myPid()) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 判断该进程是否是app进程
     *
     * @return
     */
    public static boolean isAppProcess(Context context) {
        String processName = getProcessName(context);
        if (processName == null || !processName.equalsIgnoreCase(context.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 方法描述：判断某一应用是否正在运行
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppInstalled(String apkPath) {
        PackageManager pm = BaseApplication.getsInstance().getPackageManager();
        boolean installed = false;
        try {
            PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
            if (pkgInfo != null) {
                ApplicationInfo appInfo = pkgInfo.applicationInfo;
                String packageName = appInfo.packageName; // 得到包名
                installed = true;
            } else {
                installed = false;
            }
        } catch (OutOfMemoryError e) {
            installed = false;
        }
        return installed;
    }

    public static void install(String apkFilePath) {
        File apkFile = new File(apkFilePath);
        try {
            if (apkFile.exists()) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(BaseApplication.getsInstance(), BaseApplication.getsInstance().getPackageName() + ".provider", apkFile);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(apkFile);
                }
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                BaseApplication.getsInstance().startActivity(install);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (apkFile != null) {
                apkFile.delete();
            }
        }
    }

    public static String getApkPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/xhs/120/apk/";
    }

}
