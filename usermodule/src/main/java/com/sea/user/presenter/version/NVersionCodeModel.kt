package com.sea.user.presenter.version

class NVersionCodeResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: String = ""
)

data class NVersionCodeModelReq(val command: Int = 2, var phone: String = "")