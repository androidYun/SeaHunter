package com.xhs.prison.model

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
data class NFillInformModelResponse(val code: Int, val msg: String)

class NFillInformReq(var nick_name: String = "", var avatar: String = "", val command: Int = 4)