package com.sea.user.activity.integral.detail

class NIntegralDetailModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
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

class IntegralDetailItem