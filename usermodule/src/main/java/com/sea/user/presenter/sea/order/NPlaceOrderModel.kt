package com.sea.user.presenter.sea.order

class NPlaceOrderResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: String = ""
)

class NPlaceOrderModelReq(
    private val command: Int = 14,
    var shop_id: Int = 14,//门店id
    var address_id: Int = -1,
    var remark: String = "",
    var is_invoice: Int = 0,//是否开具发票
    var pro_List: List<NShopItemMode> = listOf(),//商品集合
    var payment_id: Int = 0//支付方式（1：线下支付 2：余额支付 7：支付宝支付 8：微信支付 9：积分支付）
)

data class NShopItemMode(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_id: Int = 0,
    val quantity: Int = 0,
    val shop_id: Int = 0,
    val user_id: Int = 0
)