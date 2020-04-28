package com.sea.custom.ui.custom

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.xhs.baselibrary.utils.UIUtils

class MineCustomAdapter(mList: List<MineCustomItem>) :
    BaseQuickAdapter<MineCustomItem, BaseViewHolder>(R.layout.item_mine_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MineCustomItem) {
        helper?.setText(R.id.tvDelicacyName, item.title)
        if (item.state == 1) {
            helper?.setText(R.id.tvCustomStatusContent, "等待分配门店中")
            helper?.setTextColor(R.id.tvCustomStatusContent, UIUtils.getInstance().getColor(R.color.color_e31c1c))
            helper?.setText(R.id.ivCustomStatus, item.title)
        } else {
            helper?.setTextColor(R.id.tvCustomStatusContent, UIUtils.getInstance().getColor(R.color.color_1ca422))
            helper?.setText(R.id.tvCustomStatusContent, "门店已完成联系")
            helper?.setText(R.id.ivCustomStatus, item.title)
        }

    }
}