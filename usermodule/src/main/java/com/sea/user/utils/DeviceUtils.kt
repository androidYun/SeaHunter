package com.sea.user.utils

import android.content.res.Configuration
import com.xhs.baselibrary.BaseApplication

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/03/2020.
 * description:
 */
object DeviceUtils {
    fun isTabletDevice(): Boolean {
        return BaseApplication.getsInstance().resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}