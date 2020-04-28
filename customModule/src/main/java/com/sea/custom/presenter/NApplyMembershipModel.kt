package com.sea.custom.presenter

class NApplyMembershipResponse(
    val code: Int = -1,
    val msg: String = ""
)

class NApplyMembershipReq(
    private val command:Int=43,
    var id:Int,
    var name:String,
    var phone:String,
    var weixin:String,
    var birthday:String
)