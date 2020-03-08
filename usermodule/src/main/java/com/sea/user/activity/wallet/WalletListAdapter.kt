package com.sea.user.activity.wallet

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class WalletListAdapter(mList: List<WalletListItem>) :
    BaseQuickAdapter<WalletListItem, BaseViewHolder>(R.layout.item_wallet_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: WalletListItem?) {
        ImageLoader.loadCircleImageView(helper?.getView(R.id.ivWalletHead), "")
        helper?.setText(R.id.tvWalletTitle, "")
        helper?.setText(R.id.tvWalletTime, "")
        helper?.setText(R.id.tvWalletMoney, "")

    }
}