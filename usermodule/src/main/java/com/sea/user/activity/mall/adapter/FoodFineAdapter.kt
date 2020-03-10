package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NFoodFine

class FoodFineAdapter(mList: List<NFoodFine>) :
    BaseQuickAdapter<NFoodFine, BaseViewHolder>(R.layout.item_food_fine_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NFoodFine?) {
        helper?.setText(R.id.tvFineFoodName, "")
        helper?.addOnClickListener(R.id.lvFoodFine)
    }
}