package com.sea.custom.ui.make.list

class NDelicacyMakeListModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NDelicacyMakeListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<DelicacyMakeListItem> = listOf()
)

class DelicacyMakeListItem