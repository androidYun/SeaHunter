package com.sea.custom.ui.store

class NStoreListModelReq(
    private val command: Int = 13,
    var key: String = ""
)

class NStoreListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<StoreListItem> = listOf(),
    val totalCount: Int = 0
)


data class StoreListItem(
    val address: String = "",
    val id: Int = 0,
    val is_join: Boolean = false,
    val mobile: String = "",
    val title: String = "",
    val webchat: String = "")