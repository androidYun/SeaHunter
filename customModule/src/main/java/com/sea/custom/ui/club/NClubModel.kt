package com.sea.custom.ui.club

class NClubResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<NClubActivityItem> = listOf()
) {
    class Data
}

class NClubModelReq

class NClubActivityItem