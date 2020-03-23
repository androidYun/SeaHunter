package com.sea.user.activity.integral.mall

import android.content.Intent
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.integral.mall.detail.IntegralShopDetailActivity
import com.sea.user.activity.integral.shop.ExchangeShopActivity
import com.sea.user.common.Constants
import com.sea.user.presenter.sea.mall.MallListItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class IntegralMallAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_integral_mall_layout, mList) {
    override fun convert(helper: BaseViewHolder, item: MallListItem) {
        helper?.getView<TextView>(R.id.tvNowRedeem)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ExchangeShopActivity::class.java))
        }
        helper?.getView<ConstraintLayout>(R.id.cVIntegralShop)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, IntegralShopDetailActivity::class.java))
        }
        helper?.setText(R.id.tvShopName, item.title)
        helper?.setText(R.id.tvRedeemCount, "${item.click}人兑换")
        helper?.setText(R.id.tvShopIntegral, item.point)
        ImageLoader.loadImageWithUrl(
            helper.getView(R.id.ivSeaShop),
            Constants.baseUrl.plus(item.img_url)
        )
    }
}