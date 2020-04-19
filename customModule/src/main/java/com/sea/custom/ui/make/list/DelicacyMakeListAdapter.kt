package com.sea.custom.ui.make.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class DelicacyMakeListAdapter(mList: List<DelicacyMakeListItem>) :
    BaseQuickAdapter<DelicacyMakeListItem, BaseViewHolder>(
        R.layout.item_delicacy_make_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: DelicacyMakeListItem?) {

    }
}