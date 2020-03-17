package com.sea.user.activity.mall.order.list

import com.chad.library.adapter.base.entity.MultiItemEntity

class NShopOrderListModelReq(
    val command: Int = 15,
    var page_size: Int = 20,
    var page_index: Int = 1,
    var order_type: Int = 1,//1：商品订单 2：换购订单）
    var order_status: Int = 1//（0：全部 1：未支付 2：待发货 3：待收货 4：已完成）
)

class NShopOrderListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(
        val mList: List<ShopOrderListItem> = listOf()
    )
}

data class ShopOrderListItem(
    val accept_name: String = "",
    val add_time: String = "",
    val address: String = "",
    val area: String = "",
    val city: String = "",
    val complete_time: String = "",
    val express_no: String = "",
    val express_status: Int = 0,
    val express_time: String = "",
    val id: Int = 0,
    val order_amount: Int = 0,
    val order_no: String = "",
    val payable_amount: Int = 0,
    val payment_status: Int = 0,
    val payment_time: String = "",
    val phone: String = "",
    val point: Int = 0,
    val province: String = "",
    val real_amount: Int = 0,
    val status: Int = 0,
    val user_id: Int = 0
) : MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }
}
