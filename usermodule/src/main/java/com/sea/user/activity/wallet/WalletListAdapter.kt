package com.sea.user.activity.wallet

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class WalletListAdapter(mList: List<WalletListItem>) :
    BaseQuickAdapter<WalletListItem, BaseViewHolder>(R.layout.item_wallet_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: WalletListItem?) {

    }
}