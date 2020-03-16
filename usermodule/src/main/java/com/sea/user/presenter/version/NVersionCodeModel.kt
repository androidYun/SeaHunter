package com.sea.user.presenter.version

class NVersionCodeResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: String = ""
)

data class NVersionCodeModelReq(var phone: String = "",val command: Int = 2)