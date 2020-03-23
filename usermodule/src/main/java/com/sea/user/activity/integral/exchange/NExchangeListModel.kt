package com.sea.user.activity.integral.exchange

class NExchangeListModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NExchangeListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(

        val mList: List<ExchangeListItem> = listOf()
    )
}

class ExchangeListItem