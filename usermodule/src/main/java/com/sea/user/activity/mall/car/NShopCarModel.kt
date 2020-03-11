package com.sea.user.activity.mall.car

class NShopCarModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NShopCarModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val mList: List<ShopCarItem> = listOf()
    )
}

class ShopCarItem