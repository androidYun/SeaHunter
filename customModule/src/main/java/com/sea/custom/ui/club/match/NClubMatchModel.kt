package com.sea.custom.ui.club.match

class NClubMatchModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NClubMatchModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(
        val mList: List<ClubMatchItem> = listOf()
    )
}

class ClubMatchItem