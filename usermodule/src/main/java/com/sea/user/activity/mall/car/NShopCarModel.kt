package com.sea.user.activity.mall.car

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class NShopCarModelReq(
    val command: Int = 21,
    var shop_id: Int = -1
)

class NDeleteShopCarModelReq(
    val command: Int = 20,
    var delete_list: List<NDeleteItem> = listOf()//商品带入
)

data class NDeleteItem(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_id: Int = 0,
    val shop_id: Int = 0
)

class NEditShopCarModelReq(
    val command: Int = 19,
    var channel_id: Int = -1,//商品带入
    var article_id: Int = -1,//商品带入
    var goods_id: Int = -1,//商品
    var quantity: Int = 1,//数量
    var shop_id: Int = -1//门店Id
)

class NShopCarModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<ShopCarItem> = listOf()
)

@Parcelize
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
    var stock_quantity: Int = 0,
    val title: String = "",
    val user_price: Int = 0,
    var buyCount: Int = 1,
    var isCheck: Boolean = false
) : Parcelable