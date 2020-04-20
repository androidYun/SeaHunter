package com.sea.custom.ui.delicacy.vr.list

class NStoreVrModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NStoreVrModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<StoreVrItem> = listOf()
)

class StoreVrItem