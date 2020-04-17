package com.sea.custom.ui.collection.make

class NDelicacyMakeModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NDelicacyMakeModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<DelicacyMakeItem> = listOf()
    )
}

class DelicacyMakeItem