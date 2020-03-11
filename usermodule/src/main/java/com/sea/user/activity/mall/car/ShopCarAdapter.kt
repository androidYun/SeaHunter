package com.sea.user.activity.mall.car

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopCarAdapter(mList: List<ShopCarItem>) :
    BaseQuickAdapter<ShopCarItem, BaseViewHolder>(R.layout.item_shop_car_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopCarItem?) {

    }
}