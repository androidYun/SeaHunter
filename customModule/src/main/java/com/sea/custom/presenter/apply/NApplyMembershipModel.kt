package com.sea.custom.presenter.apply

class NApplyMembershipResponse(
    val code: Int = -1,
    val msg: String = ""
)

class NApplyMembershipReq(
    private val command: Int = 37,
    var channel_name: String = "",
    var article_id: Int = -1,
    var shop_id: Int = -1,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var webchat: String = "",
    var birthday: String = ""
)

data class NApplyMemberModel(
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var webchat: String = "",
    var birthday: String = ""
)