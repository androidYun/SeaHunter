package com.sea.user.activity.password.modify

class NModifyPasswordResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NModifyPasswordModelReq(val old_pwd: String, val new_pwd: String, val command: Int = 7)