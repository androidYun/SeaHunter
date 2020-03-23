package com.sea.user.activity.integral.mall

class NIntegralMallModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
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