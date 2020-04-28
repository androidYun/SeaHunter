package com.sea.custom.ui.store

class NStoreListModelReq(
    private val command: Int = 44,
    var province: String = "",
    var city: String = "",
    var area: String = ""
)

class NStoreListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<StoreListItem> = listOf(),
    val totalCount: Int = 0
)


data class StoreListItem(
    val VR_url: String = "",
    val address: String = "",
    val id: String = "",
    val image_url: String = "",
    val phone: String = "",
    val title: String = ""
)