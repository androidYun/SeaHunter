package com.sea.user.activity.mall.adapter

import android.content.Intent
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.detail.ShopDetailActivity
import com.sea.user.common.Constants
import com.sea.user.presenter.sea.mall.MallListItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader


class FoodRecommendAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_food_recommend_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem) {
        helper?.setText(R.id.tvRecommendName, item.title)
        helper?.setText(R.id.tvRecommendDesc, item.tags)
        helper?.setText(R.id.tvRecommendPrice, "￥${item.sell_price}")
        helper?.getView<ConstraintLayout>(R.id.cvRecommend)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ShopDetailActivity::class.java).apply {
                putExtras(ShopDetailActivity.getInstance(item.id))
            })
        }
        ImageLoader.loadCircleImageView(
            helper?.getView(R.id.ivRecommend),
            Constants.baseUrl.plus(item.img_url)
        )
    }
}