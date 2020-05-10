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
    val webchat: String = ""
)