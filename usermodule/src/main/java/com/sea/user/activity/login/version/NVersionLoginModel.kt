package com.sea.user.activity.login.version

class NVersionLoginResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}


class NVersionLoginModelReq(
    var phone: String="",
    val command: Int = 0,
    var input_code: String = "",
    var auth_code: String = ""
)