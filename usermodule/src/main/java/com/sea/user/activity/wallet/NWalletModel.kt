package com.sea.user.activity.wallet

class NWalletResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NWalletModelReq