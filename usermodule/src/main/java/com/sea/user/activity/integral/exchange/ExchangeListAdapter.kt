package com.sea.user.activity.integral.exchange

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ExchangeListAdapter(mList: List<ExchangeListItem>) :
    BaseQuickAdapter<ExchangeListItem, BaseViewHolder>(R.layout.item_exchange_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ExchangeListItem?) {

    }
}