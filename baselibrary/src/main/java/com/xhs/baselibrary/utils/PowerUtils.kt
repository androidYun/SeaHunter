package com.xhs.baselibrary.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings

import androidx.annotation.RequiresApi

import com.xhs.baselibrary.BaseApplication

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 24/12/2019.
 * description:
 */
object PowerUtils {
     val isIgnoringBatteryOptimizations: Boolean
        @RequiresApi(api = Build.VERSION_CODES.M)
        get() {
            var isIgnoring = false
            val powerManager = BaseApplication.getsInstance().getSystemService(Context.POWER_SERVICE) as PowerManager
            if (powerManager != null) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(SystemUtils.getProcessName(BaseApplication.getsInstance()))
            }
            return isIgnoring
        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestIgnoreBatteryOptimizations() {
        try {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:" + SystemUtils.getProcessName(BaseApplication.getsInstance())!!)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            BaseApplication.getsInstance().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
