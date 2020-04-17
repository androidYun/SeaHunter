package com.sea.custom.ui.membership

class NMembershipModeModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NMembershipModeModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data(),
    val totalCount: Int = 0
) {
    class Data(
        val mList: List<MembershipModeItem> = listOf()
    )
}

class MembershipModeItem