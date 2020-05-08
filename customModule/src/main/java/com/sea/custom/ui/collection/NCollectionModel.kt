package com.sea.custom.ui.collection

class NCollectionModelReq(
    val command: Int = 39,
    var channel_name: String = "",
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NCollectionModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<CollectionItem> = listOf()
)

class CollectionItem(
    val address: String,
    val albums: Any,
    val category_id: Int,
    val channel_id: Int,
    val click: Int,
    val collect_num: Int,
    val comment_num: Int,
    val content: Any,
    val eat_way: String,
    val id: Int,
    val img_url: String,
    val is_collect: Boolean,
    val mobile: String,
    val place: String,
    val product_code: String,
    val share: Int,
    val sub_title: String,
    val tags: String,
    val title: String,
    val video_src: String,
    val webchat: String,
    val weight: String,
    val worker_no: String,
    val zan: Int,
    val zhaiyao: String
)

