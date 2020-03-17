package com.sea.user.activity.mall.car

class NShopCarModelReq(
    val command: Int = 21,
    var shop_id: Int = -1
)

class NDeleteShopCarModelReq(
    val command: Int = 20,
    var channel_id: Int = -1,//商品带入
    var article_id: Int = -1,//商品带入
    var quantity: Int = -1,//数量
    var shop_id: Int = -1//门店Id
)

class NEditShopCarModelReq(
    val command: Int = 19,
    var channel_id: Int = -1,//商品带入
    var article_id: Int = -1,//商品带入
    var goods_id: Int = -1,//商品
    var quantity: Int = -1,//数量
    var shop_id: Int = -1//门店Id
)

class NShopCarModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<ShopCarItem> = listOf()
)

data class ShopCarItem(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_id: Int = 0,
    val goods_no: String = "",
    val img_url: String = "",
    val point: Int = 0,
    val quantity: Int = 0,
    val sell_price: Int = 0,
    val spec_text: String = "",
    val stock_quantity: Int = 0,
    val title: String = "",
    val user_price: Int = 0
)