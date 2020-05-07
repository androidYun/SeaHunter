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
    val collect_count: String = "",
    val comment_count: String = "",
    val good_count: String = "",
    val id: String = "",
    val image_url: String = "",
    val share_count: String = "",
    val title: String = "",
    val video_url: String = ""
)