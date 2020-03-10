package com.sea.user.activity.mall.list

class NMallListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NMallListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalcount: Int = 0,
        val mList: List<MallListItem> = listOf()
    )
}

class MallListItem