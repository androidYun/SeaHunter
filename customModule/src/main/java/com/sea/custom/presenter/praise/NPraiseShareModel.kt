package com.sea.custom.presenter.praise

class NPraiseShareResponse(
    val code: Int = -1,
    val msg: String = ""
)

data class NPraiseShareModelReq(
    private val command: Int = 36,
     var channel_name: String = "",
     var article_id: Int = -1,
     var click_type: Int = 0//1:浏览 2:点赞 3:分享

)