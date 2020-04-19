package com.sea.custom.ui.make

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class DelicacyMakeAdapter (mList: List<NMineDelicacyMakeItem>) :
    BaseQuickAdapter<NMineDelicacyMakeItem, BaseViewHolder>(
        R.layout.item_delicacy_make_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NMineDelicacyMakeItem?) {

    }
}