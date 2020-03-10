package com.sea.user.activity.mall.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NFoodRecommend

class FoodRecommendAdapter(mList: List<NFoodRecommend>) :
    BaseQuickAdapter<NFoodRecommend, BaseViewHolder>(R.layout.item_food_recommend_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NFoodRecommend?) {
        helper?.setText(R.id.tvRecommendName, "")
        helper?.setText(R.id.tvRecommendPrice, "")
        helper?.setText(R.id.tvTypeNameDesc, "")
        helper?.addOnClickListener(R.id.fvFoodType)
    }
}