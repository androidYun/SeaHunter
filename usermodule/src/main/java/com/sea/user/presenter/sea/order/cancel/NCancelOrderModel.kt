package com.sea.user.presenter.sea.order.cancel

class NCancelOrderResponse(
    val code: Int = -1,
    val msg: String = ""
)

data class NCancelOrderModelReq(private val command: Int = 16, val id: Int)