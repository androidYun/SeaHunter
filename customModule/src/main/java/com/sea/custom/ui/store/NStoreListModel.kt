package com.sea.custom.ui.store

class NStoreListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NStoreListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(
        val mList: List<StoreListItem> = listOf()
    )
}

class StoreListItem