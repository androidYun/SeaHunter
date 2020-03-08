package com.sea.user.activity.wallet

class NWalletResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<WalletListItem> = listOf()
    )
}

class WalletListItem

class NWalletModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)