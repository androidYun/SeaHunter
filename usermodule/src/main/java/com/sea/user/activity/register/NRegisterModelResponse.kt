package com.sea.user.activity.register

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
data class NRegisterModelResponse(val code: Int, val msg: String)

class NRegisterModelReq(
    var phone: String = "",
    var password: String = "",
    var inputCode: String = "",
    val command: Int = 1
)

data class NVersionCodeModelResponse(val code: Int, val msg: String)