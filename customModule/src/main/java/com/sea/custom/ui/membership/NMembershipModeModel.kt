package com.sea.custom.ui.membership

class NMembershipModeModelReq(
    private val command: Int = 42

)

class NMembershipModeModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<MembershipModeItem> = listOf(),
    val totalCount: Int = 0
)

data class MembershipModeItem(
    val address: String = "",
    val connect_person: String = "",
    val id: String = "",
    val image_url: String = "",
    val lat: String = "",
    val lon: String = "",
    val phone: String = "",
    val title: String = "",
    val weixin: String = ""
)