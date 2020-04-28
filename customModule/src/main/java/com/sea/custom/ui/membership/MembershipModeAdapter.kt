package com.sea.custom.ui.membership

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class MembershipModeAdapter(mList: List<MembershipModeItem>) :
    BaseQuickAdapter<MembershipModeItem, BaseViewHolder>(
        R.layout.item_member_ship_mode_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: MembershipModeItem) {
        helper?.setText(R.id.tvStoreName, item.title)
        helper?.setText(R.id.tvName, item.connect_person)
        helper?.setText(R.id.tvPhone, item.phone)
        helper?.setText(R.id.tvWxChat, item.weixin)
        helper?.setText(R.id.tvStoreAddress, item.address)
        helper?.addOnClickListener(R.id.tvMemberShipMode)
    }
}