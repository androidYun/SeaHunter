package com.sea.custom.ui.delicacy.introduce

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class DelicacyIntroduceAdapter(mList: List<DelicacyIntroduceItem>) :
    BaseQuickAdapter<DelicacyIntroduceItem, BaseViewHolder>(
        R.layout.item_delicacy_introduce_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: DelicacyIntroduceItem?) {

    }
}