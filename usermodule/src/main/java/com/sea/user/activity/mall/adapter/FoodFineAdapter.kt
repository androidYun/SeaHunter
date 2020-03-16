package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.presenter.sea.mall.MallListItem

class FoodFineAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_food_fine_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem?) {
        helper?.setText(R.id.tvFineFoodName, "")
        helper?.addOnClickListener(R.id.lvFoodFine)
    }
}