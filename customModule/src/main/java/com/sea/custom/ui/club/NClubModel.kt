package com.sea.custom.ui.club

class NClubResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<NClubActivityItem> = listOf()
) {
    class Data
}

class NClubModelReq(
    command: Int = 61,
    key: String = "",
    page_index: Int = 1,
    page_size: Int = 20
)

data class NClubActivityItem(
    val content: String = "",
    val id: String = "",
    val image_url: String = "",
    val title: String = ""
)