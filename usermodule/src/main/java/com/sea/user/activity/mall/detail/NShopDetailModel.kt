package com.sea.user.activity.mall.detail

import java.math.BigDecimal


data class NShopDetailResponse(
    val code: Int = 0,
    val command: Int = 0,
    val `data`: Data = Data(),
    val field_list: FieldList = FieldList(),
    val msg: String = "",
    val specs: List<ShopSpecItem> = listOf()
)

data class Data(
    val add_time: String = "",
    val albums: List<String> = listOf(),
    val attach: List<Any> = listOf(),
    val brand_id: Int = 0,
    val call_index: String = "",
    val category_id: Int = 0,
    val channel_id: Int = 0,
    val click: Int = 0,
    val content: String = "",
    val coupons: List<Any> = listOf(),
    val fields: Fields = Fields(),
    val goods: List<Good> = listOf(),
    val id: Int = 0,
    val img_url: String = "",
    val is_hot: Int = 0,
    val is_msg: Int = 0,
    val is_red: Int = 0,
    val is_slide: Int = 0,
    val is_sys: Int = 0,
    val is_top: Int = 0,
    val like_count: Int = 0,
    val link_url: String = "",
    val seo_description: String = "",
    val seo_keywords: String = "",
    val seo_title: String = "",
    val site_id: Int = 0,
    val sort_id: Int = 0,
    val specs: Any? = null,
    val status: Int = 0,
    val tags: String = "",
    val title: String = "",
    val update_time: Any? = null,
    val user_name: String = "",
    val zhaiyao: String = ""
)

data class FieldList(
    val eat_way: String = "",
    val goods_no: String = "",
    val market_price: String = "",
    val place: String = "",
    val point: String = "",
    val product_code: String = "",
    val sale_num: String = "",
    val sell_price: String = "",
    val stock_quantity: String = "",
    val weight: String = "",
    val worker_no: String = ""
)

data class ShopSpecItem(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val img_url: String = "",
    val parent_id: Int = 0,
    val son: List<ShopSpecItemSon> = listOf(),
    val spec_id: Int = 0,
    val title: String = ""
)

data class Fields(
    val eat_way: String = "",
    val goods_no: String = "",
    val market_price: String = "",
    val place: String = "",
    val point: String = "",
    val product_code: String = "",
    val sale_num: String = "",
    val sell_price: String = "",
    val stock_quantity: String = "",
    val weight: String = "",
    val worker_no: String = ""
)

data class Good(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_no: String = "",
    val group_prices: Any? = null,
    val id: Int = 0,
    val market_price: Double = 0.0,
    val sell_price: Double = 0.0,
    val spec_ids: String = "",
    val spec_text: String = "",
    val stock_quantity: Int = 0
)

data class ShopSpecItemSon(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val img_url: String = "",
    val parent_id: Int = 0,
    val spec_id: Int = 0,
    val title: String = "",
    var isSelect: Boolean = false
)

class NShopDetailModelReq(val command: Int = 10, var good_id: Int = -1)

data class NShopDetailModel(
    val id: Int = -1,
    val bannerList: List<String> = listOf(),
    val paramsList: List<NShopParamsItem> = listOf(),
    val title: String = "",
    val content: String = "",
    val tags: String = "",
    val saleNumber: String = "",
    val stockQuantity: Int = 0,//库存
    val sellPrice: BigDecimal = BigDecimal(0),
    val imageUrl: String = "",
    val channelId: Int = -1,
    val specs: List<ShopSpecItem> = listOf(),
    val goods: List<Good> = listOf(),
    val point: String = ""
)

data class NShopParamsItem(val title: String, val value: String)