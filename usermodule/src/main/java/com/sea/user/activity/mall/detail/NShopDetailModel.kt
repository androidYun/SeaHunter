package com.sea.user.activity.mall.detail

class NShopDetailResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
)

data class Data(
    val category_id: Int = 0,
    val click: Int = 0,
    val content: String = "",
    val fields: List<Field> = listOf(),
    val goods: List<Good> = listOf(),
    val id: Int = 0,
    val img_url: String = "",
    val is_hot: Int = 0,
    val is_msg: Int = 0,
    val is_red: Int = 0,
    val is_top: Int = 0,
    val specs: List<Spec> = listOf(),
    val tags: String = "",
    val title: String = ""
)

data class Field(
    val eat_way: String = "",
    val goods_no: String = "",
    val market_price: Int = 0,
    val place: String = "",
    val point: Int = 0,
    val product_code: String = "",
    val sell_price: Int = 0,
    val stock_quantity: Int = 0,
    val weight: String = "",
    val worker_no: String = ""
)

data class Good(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_no: String = "",
    val id: Int = 0,
    val market_price: Int = 0,
    val sell_price: Int = 0,
    val spec_ids: String = "",
    val stock_quantity: Int = 0
)

data class Spec(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val img_url: Int = 0,
    val parent_id: Int = 0,
    val spec_id: Int = 0,
    val title: Int = 0
)

class NShopDetailModelReq(val command: Int = 10, var good_id: Int = -1)