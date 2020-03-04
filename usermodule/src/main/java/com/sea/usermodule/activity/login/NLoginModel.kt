package com.sea.usermodule.activity.login

class NLoginResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NLoginModelReq