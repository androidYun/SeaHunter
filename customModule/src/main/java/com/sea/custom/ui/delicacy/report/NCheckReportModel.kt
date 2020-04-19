package com.sea.custom.ui.delicacy.report

class NCheckReportModelReq(
    var pageSize: Int = 20,
    var pageIndex: Int = 1
)

class NCheckReportModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<CheckReportItem> = listOf()
)

class CheckReportItem