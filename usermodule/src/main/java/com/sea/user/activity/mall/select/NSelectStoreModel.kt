package com.sea.user.activity.mall.select

class NSelectStoreModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NSelectStoreModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<SelectStoreItem> = listOf()
    )
}

class SelectStoreItem