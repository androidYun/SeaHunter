package com.sea.user.activity.mall.select

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class SelectStoreAdapter(mList: List<SelectStoreItem>) :
    BaseQuickAdapter<SelectStoreItem, BaseViewHolder>(R.layout.item_select_store_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: SelectStoreItem?) {
        helper?.setText(R.id.tvStoreName, "")
        helper?.setText(R.id.tvStoreAddress, "")
        helper?.addOnClickListener(R.id.cvSelectStore)
    }
}