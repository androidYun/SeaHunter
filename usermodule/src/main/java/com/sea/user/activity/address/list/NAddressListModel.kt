package com.sea.user.activity.address.list

class NAddressListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NAddressListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val mList: List<AddressListItem> = listOf()
    )
}

class AddressListItem