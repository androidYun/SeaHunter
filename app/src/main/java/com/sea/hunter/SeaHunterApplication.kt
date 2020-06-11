package com.sea.hunter

import com.crashlytics.android.Crashlytics
import com.sea.hunter.router.SeaRouterImpl
import com.sea.publicmodule.router.RouterManager
import com.sea.user.base.UserBaseParamsProvide
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.init.BaseParamsClient
import io.fabric.sdk.android.Fabric


class SeaHunterApplication : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        initBaseProvide()
        initRouter()
        Fabric.with(this, Crashlytics()) //
        ZXingLibrary.initDisplayOpinion(this)
    }

    private fun initRouter() {
        RouterManager.seaRouter = SeaRouterImpl()
    }

    private fun initBaseProvide() {
        BaseParamsClient.getInstance().init(UserBaseParamsProvide())

    }

}