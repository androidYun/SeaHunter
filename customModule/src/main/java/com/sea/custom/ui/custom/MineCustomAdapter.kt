package com.sea.custom.ui.custom

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class MineCustomAdapter(mList: List<MineCustomItem>) :
    BaseQuickAdapter<MineCustomItem, BaseViewHolder>(R.layout.item_mine_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MineCustomItem?) {

    }
}