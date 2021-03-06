package com.sea.user.activity.shop.order

class NShopOrderModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NShopOrderModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<ShopOrderItem> = listOf()
    )
}

class ShopOrderItem