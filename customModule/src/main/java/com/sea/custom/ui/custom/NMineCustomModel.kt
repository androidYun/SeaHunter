package com.sea.custom.ui.custom

class NMineCustomModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NMineCustomModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(

        val mList: List<MineCustomItem> = listOf()
    )
}

class MineCustomItem