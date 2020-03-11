package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NFoodType

class FoodTypeAdapter(mList: List<NFoodType>) :
    BaseQuickAdapter<NFoodType, BaseViewHolder>(R.layout.item_food_type_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NFoodType) {
        helper?.setText(R.id.tvTypeName, item.typeName)
        helper?.setText(R.id.tvTypeNameDesc, item.typeDesc)
        helper?.addOnClickListener(R.id.fvFoodType)
        helper?.setImageResource(R.id.ivFoodTypeBg, item.resId)
    }
}