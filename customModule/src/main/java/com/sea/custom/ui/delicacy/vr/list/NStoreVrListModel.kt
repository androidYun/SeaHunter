package com.sea.custom.ui.delicacy.vr.list

class NStoreVrModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NStoreVrModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<StoreVrItem> = listOf()
)

class StoreVrItem