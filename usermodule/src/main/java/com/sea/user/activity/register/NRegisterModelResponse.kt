package com.sea.user.activity.register
/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com

 * @ date 31/12/2019.
 * description:
 */
data class NRegisterModelResponse(val code: Int, val msg: String, val data: String = "")

class NRegisterModelReq(
    var phone: String = "",
    var password: String = "",
    var input_code: String = "",
    var auth_code: String = "",
    var user_id: Int = -1,
    val command: Int = 1
)

data class NVersionCodeModelResponse(val code: Int, val msg: String)