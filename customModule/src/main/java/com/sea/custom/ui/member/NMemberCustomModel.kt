package com.sea.custom.ui.member

class NMemberCustomModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NMemberCustomModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalcount: Int = 0,
        val mList: List<MemberCustomItem> = listOf()
    )
}

class MemberCustomItem