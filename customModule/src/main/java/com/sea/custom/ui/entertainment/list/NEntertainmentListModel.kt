package com.sea.custom.ui.entertainment.list

class NEntertainmentListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NEntertainmentListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: Data = Data()
) {
    class Data(
        val mList: List<EntertainmentListItem> = listOf()
    )
}

class EntertainmentListItem