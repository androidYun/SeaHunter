package com.sea.publicmodule.activity.search




class NSearchMallModelReq(
    var command: Int = 26
)

class NAddSearchMallModelReq(
    val command: Int = 23,
    var keyword:String=""
)

class NSearchModelReq(
    val command: Int = 25
)


data class NSearchMallModelResponse(
    val code: Int? = 0,
    val command: Int? = 0,
    val `data`: List<SearchItem> = listOf(),
    val msg: String? = ""
)

data class SearchItem(
    val id: Int? = 0,
    val keyword: String? = ""
)


