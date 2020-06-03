package com.sea.custom.presenter.channel.detail

import com.sea.custom.presenter.channel.NChannelItem

class NChannelDetailResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: NChannelItem = NChannelItem()
)

class NChannelDetailModelReq(
    private val command: Int = 31,
    var channel_name: String = "",
    var id: Int = -1
)