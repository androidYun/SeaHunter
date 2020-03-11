package com.sea.user.activity.mall

class NMallConfirmOrderModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NMallConfirmOrderModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val mList: List<MallConfirmOrderItem> = listOf()
    )
}

class MallConfirmOrderItem