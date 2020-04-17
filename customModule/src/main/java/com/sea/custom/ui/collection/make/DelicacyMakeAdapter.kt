package com.sea.custom.ui.collection.make

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class DelicacyMakeAdapter(mList: List<DelicacyMakeItem>) :
    BaseQuickAdapter<DelicacyMakeItem, BaseViewHolder>(
        R.layout.item_activity_delicacy_make_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: DelicacyMakeItem?) {

    }
}