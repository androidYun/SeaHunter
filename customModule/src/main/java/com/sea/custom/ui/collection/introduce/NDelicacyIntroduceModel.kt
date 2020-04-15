package com.sea.custom.ui.collection.introduce

class NDelicacyIntroduceModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NDelicacyIntroduceModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<DelicacyIntroduceItem> = listOf()
    )
}

class DelicacyIntroduceItem