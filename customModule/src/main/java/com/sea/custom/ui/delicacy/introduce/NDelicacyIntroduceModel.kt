package com.sea.custom.ui.delicacy.introduce

class NDelicacyIntroduceModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NDelicacyIntroduceModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<DelicacyIntroduceItem> = listOf()
)

class DelicacyIntroduceItem