package com.sea.custom.ui.custom

class NMineCustomModelReq(
    private val command: Int = 41,
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
    val id: String = "",
    val image_url: String = "",
    val state: Int = 1,//1、等待分配中；2、门店已完成分配
    val title: String = "",
    val video_url: String = ""
)