package com.sea.custom.ui.collection.introduce

class NDelicacyIntroduceModelReq(
    private val command: Int = 40,
    private val type: Int = 1,
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NDelicacyIntroduceModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<DelicacyIntroduceItem> = listOf()
)

class DelicacyIntroduceItem(
    val collect_count: String = "",
    val comment_count: String = "",
    val good_count: String = "",
    val id: String = "",
    val image_url: String = "",
    val share_count: String = "",
    val title: String = "",
    val video_url: String = ""
)