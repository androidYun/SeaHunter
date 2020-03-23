package com.sea.user.activity.wallet



class NRechargeDetailResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<RechargeDetailListItem> = listOf()
    )
}
data class RechargeDetailListItem(
    val amount: Number = 0,
    val complete_time: String = "",
    val payment: String = "",
    val recharge_no: String = ""
)

class NRechargeDetailReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)