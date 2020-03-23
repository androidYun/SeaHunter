package com.sea.user.activity.mall.order.confirm

class NMallConfirmOrderModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
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