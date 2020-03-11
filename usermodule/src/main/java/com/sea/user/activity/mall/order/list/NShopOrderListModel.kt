package com.sea.user.activity.mall.order.list

class NShopOrderListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NShopOrderListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<ShopOrderListItem> = listOf()
    )
}

class ShopOrderListItem