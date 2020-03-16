package com.sea.user.presenter.store

class NStoreListResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<NStoreListItemModel> = listOf()
)

data class NStoreListItemModel(
    val address: String = "",
    val id: Int = 0,
    val mobile: String = "",
    val title: String = ""
)

class NStoreListModelReq(val command: Int = 13, var key: String="")