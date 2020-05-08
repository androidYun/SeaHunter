package com.sea.custom.presenter.channel.detail

class NChannelDetailResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: ChannelDetail = ChannelDetail()
)

class NChannelDetailModelReq(
    private val command: Int = 31,
    var channel_name: String = "",
    var id: Int = -1
)

data class ChannelDetail(
    val address: String = "",
    val albums: List<Any> = listOf(),
    val category_id: Int = 0,
    val channel_id: Int = 0,
    val click: Int = 0,
    val collect_num: Int = 0,
    val comment_num: Int = 0,
    val content: String = "",
    val eat_way: String = "",
    val id: Int = 0,
    val img_url: String = "",
    val is_collect: Boolean = false,
    val mobile: String = "",
    val place: String = "",
    val product_code: String = "",
    val share: Int = 0,
    val sub_title: String = "",
    val tags: String = "",
    val title: String = "",
    val video_src: String = "",
    val webchat: String = "",
    val weight: String = "",
    val worker_no: String = "",
    val zan: Int = 0
)