package com.sea.custom.ui.store

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class StoreListAdapter(mList: List<StoreListItem>) :
    BaseQuickAdapter<StoreListItem, BaseViewHolder>(R.layout.item_store_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: StoreListItem?) {

    }
}