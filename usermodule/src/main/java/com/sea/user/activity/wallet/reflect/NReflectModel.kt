package com.sea.user.activity.wallet.reflect

class NReflectResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NReflectModelReq