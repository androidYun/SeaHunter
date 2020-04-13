package com.sea.custom.ui.delicacy

class NDelicacyResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NDelicacyModelReq