package com.sea.user.activity.mall.search

class NSearchStoreModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NSearchStoreModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<SearchStoreItem> = listOf()
    )
}

class SearchStoreItem