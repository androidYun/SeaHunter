package com.sea.hunter

import com.sea.user.base.UserBaseParamsProvide
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.init.BaseParamsClient

class SeaHunterApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initBaseProvide()
    }

    private fun initBaseProvide() {
        BaseParamsClient.getInstance().init(UserBaseParamsProvide())
    }
}