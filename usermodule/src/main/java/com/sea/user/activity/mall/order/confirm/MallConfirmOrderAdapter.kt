package com.sea.user.activity.mall.order.confirm

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class MallConfirmOrderAdapter(mList: List<MallConfirmOrderItem>) :
    BaseQuickAdapter<MallConfirmOrderItem, BaseViewHolder>(R.layout.item_mall_confirm_order_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallConfirmOrderItem?) {

    }
}