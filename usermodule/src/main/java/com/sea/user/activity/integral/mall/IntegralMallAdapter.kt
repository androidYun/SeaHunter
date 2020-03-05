package com.sea.user.activity.integral.mall

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class IntegralMallAdapter(mList: List<IntegralMallItem>) :
    BaseQuickAdapter<IntegralMallItem, BaseViewHolder>(R.layout.item_integral_mall_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: IntegralMallItem?) {

    }
}