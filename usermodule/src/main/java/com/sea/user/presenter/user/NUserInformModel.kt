package com.sea.user.presenter.user

import com.sea.user.activity.login.UserInformModel

class NUserInformResponse(
    val code: Int = 0,
    val command: Int = 0,
    val `data`: UserInformModel = UserInformModel(),
    val msg: String = ""
)


data class NUserInformReq(private val command: Int = 5)