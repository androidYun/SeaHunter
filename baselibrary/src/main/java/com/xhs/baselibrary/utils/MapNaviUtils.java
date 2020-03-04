package com.xhs.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.xhs.baselibrary.R;

import java.util.List;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 30/04/2019.
 * description:
 */
public class MapNaviUtils {

    public static void goToMap(Context context, String mLat, String mLng, String mAddressStr) {
        if (isInstalled(context, "com.baidu.BaiduMap")) {
            goToBaiDuMap(context, mLat, mLng, mAddressStr);
        } else if (isInstalled(context, "com.autonavi.minimap")) {
            goToGaoDeMap(context, mAddressStr);
        } else {
           ToastUtils.show(UIUtils.getInstance().getString(R.string.base_please_install_bai_du_and_gao_de_map));
        }

    }


    /**
     * 跳转百度地图
     */
    private static void goToBaiDuMap(Context context, String mLat, String mLng, String mAddressStr) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + mLat + ","
                + mLng + "|name:" + mAddressStr + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + context.getPackageName()));
        context.startActivity(intent); // 启动调用
    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    private static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * 跳转高德地图
     */
    private static void goToGaoDeMap(Context context, String mAddressStr) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://keywordNavi?sourceApplication=").append("amap").append("&keyword=" + mAddressStr);
        stringBuffer.append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

}
