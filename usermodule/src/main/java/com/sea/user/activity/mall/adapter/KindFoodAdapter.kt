package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NKindFood

class KindFoodAdapter(mList: List<NKindFood>) :
    BaseQuickAdapter<NKindFood, BaseViewHolder>(R.layout.item_food_type_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NKindFood?) {
        helper?.setText(R.id.tvKindName, "")
        helper?.addOnClickListener(R.id.lvFoodKind)
    }
}