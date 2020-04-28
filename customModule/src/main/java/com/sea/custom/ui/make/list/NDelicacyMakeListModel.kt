package com.sea.custom.ui.make.list

class NDelicacyMakeListModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NDelicacyMakeListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<DelicacyMakeListItem> = listOf()
)

class DelicacyMakeListItem