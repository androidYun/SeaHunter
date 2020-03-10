package com.sea.user.activity.mall.adapter

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.NKindFood
import com.sea.user.activity.mall.list.MallListActivity

class KindFoodAdapter(mList: List<NKindFood>) :
    BaseQuickAdapter<NKindFood, BaseViewHolder>(R.layout.item_food_kind_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NKindFood) {
        helper?.setText(R.id.tvKindName, item.kindName)
        helper?.setImageResource(R.id.ivKindFood, item.resId)
        helper?.getView<LinearLayout>(R.id.lvFoodKind)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, MallListActivity::class.java))
        }
    }
}