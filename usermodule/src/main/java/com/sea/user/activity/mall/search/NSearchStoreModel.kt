package com.sea.user.activity.mall.search

class NSearchStoreModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)


class NHotSearchStoreModelReq(
    var command: Int = 27
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