package com.sea.user.activity.feedback.list

class NFeedBackListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NFeedBackListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<FeedBackListItem> = listOf()
    )
}

class FeedBackListItem