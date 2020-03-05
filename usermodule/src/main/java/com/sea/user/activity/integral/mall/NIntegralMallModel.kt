package com.sea.user.activity.integral.mall

class NIntegralMallModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NIntegralMallModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val mList: List<IntegralMallItem> = listOf()
    )
}

class IntegralMallItem