package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.presenter.sea.mall.MallListItem

class FoodRecommendAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_food_recommend_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem?) {
        helper?.setText(R.id.tvRecommendName, "")
        helper?.setText(R.id.tvRecommendPrice, "")
        helper?.addOnClickListener(R.id.fvFoodType)
    }
}