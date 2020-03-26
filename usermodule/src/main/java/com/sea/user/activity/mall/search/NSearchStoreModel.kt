package com.sea.user.activity.mall.search

class NSearchMallModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)


class NHotSearchMallModelReq(
    var command: Int = 27
)

class NAddSearchMallModelReq(
    val command: Int = 23,
    var keyword:String=""
)

class NSearchMallModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<String> = listOf()
)

class SearchMallItem