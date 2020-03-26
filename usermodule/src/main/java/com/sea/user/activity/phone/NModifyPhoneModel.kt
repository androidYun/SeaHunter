package com.sea.user.activity.phone

class NModifyPhoneResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NModifyPhoneModelReq(private val command: Int = 6, val mobile: String = "")