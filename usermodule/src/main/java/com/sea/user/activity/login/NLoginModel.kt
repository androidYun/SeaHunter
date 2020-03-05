package com.sea.user.activity.login

class NLoginResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NLoginModelReq