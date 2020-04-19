package com.sea.custom.ui.delicacy.report

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class CheckReportAdapter(mList: List<CheckReportItem>) :
    BaseQuickAdapter<CheckReportItem, BaseViewHolder>(R.layout.item_check_report_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: CheckReportItem?) {

    }
}