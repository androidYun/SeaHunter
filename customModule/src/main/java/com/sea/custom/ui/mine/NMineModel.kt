package com.sea.custom.ui.mine

class NMineResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NMineModelReq