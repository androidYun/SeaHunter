package com.sea.user.activity.mall.order.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopOrderListAdapter(mList: List<ShopOrderListItem>) :
    BaseQuickAdapter<ShopOrderListItem, BaseViewHolder>(R.layout.item_shop_order_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopOrderListItem?) {

    }
}