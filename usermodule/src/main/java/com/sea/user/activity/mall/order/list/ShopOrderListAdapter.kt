package com.sea.user.activity.mall.order.list

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopOrderListAdapter(mList: List<ShopOrderListItem>) :
    BaseMultiItemQuickAdapter<ShopOrderListItem, BaseViewHolder>(mList) {
    init {
        addItemType(1, R.layout.item_wait_pay_shop_order)
        addItemType(2, R.layout.item_ship_shop_order)
        addItemType(3, R.layout.item_wait_received_shop_order)
        addItemType(4, R.layout.item_finish_shop_order)
    }


    override fun convert(helper: BaseViewHolder, item: ShopOrderListItem?) {
        when (getItemViewType(helper.layoutPosition)) {
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
        }
    }
}