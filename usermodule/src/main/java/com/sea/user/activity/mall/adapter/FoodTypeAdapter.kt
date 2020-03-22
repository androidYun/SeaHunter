package com.sea.user.activity.mall.adapter

import android.content.Intent
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NFoodType
import com.sea.user.activity.mall.list.MallListActivity

class FoodTypeAdapter(mList: List<NFoodType>) :
    BaseQuickAdapter<NFoodType, BaseViewHolder>(R.layout.item_food_type_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NFoodType) {
        helper?.setText(R.id.tvTypeName, item.typeName)
        helper?.setText(R.id.tvTypeNameDesc, item.typeDesc)
        helper?.getView<FrameLayout>(R.id.fvFoodType)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, MallListActivity::class.java).apply {
                putExtras(MallListActivity.getInstance(plate = helper.layoutPosition + 1))
            })

        }
        helper?.setImageResource(R.id.ivFoodTypeBg, item.resId)

    }
}