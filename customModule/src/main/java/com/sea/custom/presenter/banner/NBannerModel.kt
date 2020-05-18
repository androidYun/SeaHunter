package com.sea.custom.presenter.banner

class NBannerResponse(
    val code: Int = 0,
    val `data`: List<BannerItem> = listOf(),
    val msg: String = ""
) {

}

data class BannerItem(
    val goods_id: Int = 0,
    val img_url: String = ""
)

class NBannerModelReq(private val command: Int = 28, var channel_name: String = "")