package com.sea.user.activity.mall.order.list

import com.chad.library.adapter.base.entity.MultiItemEntity

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

class ShopOrderListItem : MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }
}