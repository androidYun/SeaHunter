package com.sea.user.activity.wallet

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class WalletListAdapter(mList: List<RechargeDetailListItem>) :
    BaseQuickAdapter<RechargeDetailListItem, BaseViewHolder>(R.layout.item_wallet_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: RechargeDetailListItem) {
        ImageLoader.loadCircleImageView(helper?.getView(R.id.ivWalletHead), "")
        helper?.setText(R.id.tvWalletTitle, item.payment)
        helper?.setText(R.id.tvWalletTime, item.complete_time)
        helper?.setText(R.id.tvWalletMoney, item.amount.toString())

    }
}