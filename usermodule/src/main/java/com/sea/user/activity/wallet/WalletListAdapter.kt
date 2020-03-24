package com.sea.user.activity.wallet

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class WalletListAdapter(private val walletType: Int, mList: List<RechargeDetailListItem>) :
    BaseQuickAdapter<RechargeDetailListItem, BaseViewHolder>(
        R.layout.item_wallet_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: RechargeDetailListItem) {
        helper?.getView<ImageView>(R.id.ivWalletHead)
            ?.setBackgroundResource(if (walletType == WalletFragment.WALLET_BALANCE_CODE) R.mipmap.ic_topup else R.mipmap.ic_withdrawal)
        helper?.setText(R.id.tvWalletTitle, item.payment)
        helper?.setText(R.id.tvWalletTime, item.complete_time)
        helper?.setText(R.id.tvWalletMoney, item.amount.toString())

    }
}