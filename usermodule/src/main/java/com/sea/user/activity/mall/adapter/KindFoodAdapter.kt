package com.sea.user.activity.mall.adapter

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.SeaCategoryItemModel
import com.sea.user.activity.mall.list.MallListActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class KindFoodAdapter(mList: List<SeaCategoryItemModel>) :
    BaseQuickAdapter<SeaCategoryItemModel, BaseViewHolder>(R.layout.item_food_kind_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: SeaCategoryItemModel) {
        helper?.setText(R.id.tvKindName, item.title)
        ImageLoader.loadImageWithUrl(helper?.getView(R.id.ivKindFood), item.img_url)
        helper?.getView<LinearLayout>(R.id.lvFoodKind)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, MallListActivity::class.java))
        }
    }
}