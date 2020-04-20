package com.sea.custom.ui.delicacy.vr.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class StoreVrListAdapter(mList: List<StoreVrItem>) :
    BaseQuickAdapter<StoreVrItem, BaseViewHolder>(
        R.layout.item_activity_store_vr_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: StoreVrItem?) {

    }
}