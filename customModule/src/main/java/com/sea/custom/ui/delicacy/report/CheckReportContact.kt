package com.sea.custom.ui.delicacy.report

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface CheckReportContact {

    interface ICheckReportView : IBaseView {

        fun loadCheckReportSuccess(mList: List<CheckReportItem>, totalCount: Int)

        fun loadCheckReportFail(throwable: Throwable)

    }

    interface ICheckReportPresenter {
        fun loadCheckReport(nCheckReportModelReq: NCheckReportModelReq)
    }
}