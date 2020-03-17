package com.sea.user.presenter.sea.order

class NPlaceOrderResponse(
    val code: Int = -1,
    val msg: String = ""
)

class NPlaceOrderModelReq(
    val command: Int = 14,
    val shop_id: Int = 14,//门店id
    val address_id: Int = -1,
    val remark: String = "",
    val is_invoice: Int = 0,//是否开具发票
    val pro_List: Int = 14,//商品集合
    val payment_id: Int = 0//支付方式（1：线下支付 2：余额支付 7：支付宝支付 8：微信支付 9：积分支付）
)