package com.sea.custom.ui.entertainment.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class EntertainmentListAdapter(mList: List<EntertainmentListItem>) :
    BaseQuickAdapter<EntertainmentListItem, BaseViewHolder>(
        R.layout.item_fragment_entertainment_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: EntertainmentListItem?) {

    }
}