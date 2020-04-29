package com.sea.custom.presenter.category

class NCategoryResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<NCategoryItem> = listOf()
)

data class NCategoryItem(
    val id: Int = 0,
    val img_url: String = "",
    val layer: Int = 0,
    val title: String = ""
)

class NCategoryModelReq(
   private val command: Int = 8,
    val channel_name: String = ""
)