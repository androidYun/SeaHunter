package com.sea.custom.ui.delicacy.report

class NCheckReportModelReq(
    var page_size: Int = 20,
    var page_index: Int = 1
)

class NCheckReportModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<CheckReportItem> = listOf()
)

class CheckReportItem