package com.sea.user.activity.mall.order.detail

class NMallOrderDetailResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<NMallOrderDetailListItem> = listOf()
    )
}
class NMallOrderDetailListItem

class NMallOrderDetailModelReq