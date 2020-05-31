package com.sea.publicmodule.presenter.version

class NCheckVersionResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: VersionModel = VersionModel()
)

data class VersionModel(
    val add_time: String = "",
    val android_url: String = "",
    val apple_url: String = "",
    val forced: Int = -1,////:0/1 是否强制更新,
    val version: String = ""
)

class NCheckVersionModelReq(private val command: Int = 100, var versionName: String = "")