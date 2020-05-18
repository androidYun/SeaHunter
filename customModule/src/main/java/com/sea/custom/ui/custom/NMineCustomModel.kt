package com.sea.custom.ui.custom

class NMineCustomModelReq(
    private val command: Int = 38,
    var channel_name: String = "",
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NMineCustomModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<MineCustomItem> = listOf(),
    val totalCount: Int = 0
)



data class MineCustomItem(
    val add_time: String = "",
    val address: String = "",
    val article_id: Int = 0,
    val birthday: String = "",
    val channel_id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val shop_id: Int = 0,
    val user_id: String = "",
    val webchat: String = "",
    val article: Article? = Article()
)

data class Article(
    val add_time: String? = "",
    val albums: List<String> = listOf(),
    val attach: List<String> = listOf(),
    val brand_id: Int? = 0,
    val call_index: String? = "",
    val category_id: Int? = 0,
    val channel_id: Int? = 0,
    val click: Int? = 0,
    val content: String? = "",
    val coupons: List<Any?>? = listOf(),
    val fields: Fields? = Fields(),
    val goods: List<Any?>? = listOf(),
    val id: Int? = 0,
    val img_url: String? = "",
    val is_hot: Int? = 0,
    val is_msg: Int? = 0,
    val is_red: Int? = 0,
    val is_slide: Int? = 0,
    val is_sys: Int? = 0,
    val is_top: Int? = 0,
    val like_count: Int? = 0,
    val link_url: String? = "",
    val seo_description: String? = "",
    val seo_keywords: String? = "",
    val seo_title: String? = "",
    val share: Int? = 0,
    val site_id: Int? = 0,
    val sort_id: Int? = 0,
    val specs: Any? = Any(),
    val status: Int? = 0,
    val tags: String? = "",
    val title: String? = "",
    val update_time: String? = "",
    val user_name: String? = "",
    val zan: Int? = 0,
    val zhaiyao: String? = ""
)

data class Fields(
    val video_src: String? = ""
)