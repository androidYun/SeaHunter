package com.sea.custom.ui.member

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class MemberCustomAdapter(mList: List<MemberCustomItem>) :
    BaseQuickAdapter<MemberCustomItem, BaseViewHolder>(R.layout.item_member_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MemberCustomItem?) {

    }
}