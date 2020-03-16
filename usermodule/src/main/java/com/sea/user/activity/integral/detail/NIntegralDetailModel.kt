package com.sea.user.activity.integral.detail

class NIntegralDetailModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1,
    val command: Int = 17
)

class NIntegralDetailModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<IntegralDetailItem> = listOf()
    )
}

data class IntegralDetailItem(
    val add_time: String = "",
    val point: Int = 0,
    val title: String = ""
)