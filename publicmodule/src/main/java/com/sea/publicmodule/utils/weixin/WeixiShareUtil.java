package com.sea.publicmodule.utils.weixin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class WeixiShareUtil {
    public static String getWeixinAppId() {
        return "wxfae4605c7d50263a";
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 10 && options != 10) {
            output.reset(); //清空output
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        if (needRecycle) {
            bmp.recycle();
        }
        return output.toByteArray();
    }
    /**
     * 判断手机是否安装微信
     *
     * @param context 上下文
     * @return
     */
    public static boolean isWxAppInstalledAndSupported(Context context) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, getWeixinAppId());

        boolean bIsWXAppInstalledAndSupported = wxApi .isWXAppInstalled() && wxApi.isWXAppInstalled();
        if (!bIsWXAppInstalledAndSupported) {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equals("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }

        return true;
    }
}
