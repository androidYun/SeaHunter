package com.sea.user.activity.password

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
data class NForgetPasswordModelResponse(val code: Int, val msg: String)


data class NForgetPasswordModelReq(
    var auth_code: String = "",
    val command: Int = 101,
    var input_code: String = "",
    var password: String = "",
    var phone: String = ""
)