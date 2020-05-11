package com.sea.hunter

import com.sea.hunter.router.SeaRouterImpl
import com.sea.user.base.UserBaseParamsProvide
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.init.BaseParamsClient
import com.xhs.publicmodule.router.RouterManager

class SeaHunterApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initBaseProvide()
        initRouter()
    }

    private fun initRouter() {
        RouterManager.seaRouter = SeaRouterImpl()
    }

    private fun initBaseProvide() {
        BaseParamsClient.getInstance().init(UserBaseParamsProvide())

    }
}