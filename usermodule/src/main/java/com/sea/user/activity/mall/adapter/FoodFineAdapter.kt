package com.sea.user.activity.mall.adapter

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.detail.ShopDetailActivity
import com.sea.user.common.Constants
import com.sea.user.presenter.sea.mall.MallListItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class FoodFineAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_food_fine_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem) {
        helper?.setText(R.id.tvFineFoodName, item.title)
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivFoodFine),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<LinearLayout>(R.id.lvFoodFine)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ShopDetailActivity::class.java).apply {
                putExtras(ShopDetailActivity.getInstance(item.id))
            })
        }
    }
}