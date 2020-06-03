package com.sea.custom.ui.collection

import com.sea.custom.presenter.channel.NChannelItem

class NCollectionModelReq(
    val command: Int = 39,
    var channel_name: String = "",
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NCollectionModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<NChannelItem> = listOf()
)


