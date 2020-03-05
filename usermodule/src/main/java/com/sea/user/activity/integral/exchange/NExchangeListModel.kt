package com.sea.user.activity.integral.exchange

class NExchangeListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NExchangeListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val mList: List<ExchangeListItem> = listOf()
    )
}

class ExchangeListItem