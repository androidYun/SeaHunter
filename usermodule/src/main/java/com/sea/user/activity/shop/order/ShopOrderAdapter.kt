package com.sea.user.activity.shop.order

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopOrderAdapter(mList: List<ShopOrderItem>) :
    BaseQuickAdapter<ShopOrderItem, BaseViewHolder>(R.layout.item_shop_order_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopOrderItem?) {

    }
}