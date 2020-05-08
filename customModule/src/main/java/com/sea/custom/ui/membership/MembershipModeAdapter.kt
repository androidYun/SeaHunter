package com.sea.custom.ui.membership

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.presenter.channel.NChannelItem

class MembershipModeAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_member_ship_mode_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        helper?.setText(R.id.tvStoreName, item.title)
        helper?.setText(R.id.tvName, item.place)
        helper?.setText(R.id.tvPhone, item.mobile)
        helper?.setText(R.id.tvWxChat, item.webchat)
        helper?.setText(R.id.tvStoreAddress, item.address)
        helper?.addOnClickListener(R.id.tvMemberShipMode)
    }
}