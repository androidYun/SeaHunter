package com.xhs.baselibrary.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import android.text.TextUtils;

import com.xhs.baselibrary.BaseApplication;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 11/04/2019.
 * description:
 */
public class CallUtils {


    /**
     * 打电话
     *
     * @param phoneNum
     */
    public static void callPhone(Context context,String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.show("手机号码为空");
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.parse("tel:" + phoneNum);
            intent.setData(uri);
            if (ActivityCompat.checkSelfPermission(BaseApplication.getsInstance(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intent);
        }
    }

}
