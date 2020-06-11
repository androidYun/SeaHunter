package com.sea.hunter

import com.sea.hunter.router.SeaRouterImpl
import com.sea.publicmodule.router.RouterManager
import com.sea.user.base.UserBaseParamsProvide
import com.tencent.bugly.crashreport.CrashReport
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.init.BaseParamsClient


class SeaHunterApplication : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        initBaseProvide()
        initRouter()
        ZXingLibrary.initDisplayOpinion(this)
        //正式环境获取bug
        CrashReport.initCrashReport(applicationContext, "d9624127c7", !BuildConfig.DEBUG)
    }

    private fun initRouter() {
        RouterManager.seaRouter = SeaRouterImpl()
    }

    private fun initBaseProvide() {
        BaseParamsClient.getInstance().init(UserBaseParamsProvide())

    }

}