package com.sea.user.activity.address

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
data class NAddressModelResponse(val code: Int, val msg: String)

class NAddressModelReq(
    val command: Int = 11,
    var accept_name: String = "",
    var mobile: String = "",
    var province: String = "",
    var city: String = "",
    var area: String = "",
    var address: String = "",
    var is_default: Int = 0//是否默认0 不是 1是（0,1）

)

data class NAddressDetailModelResponse(val code: Int, val msg: String)