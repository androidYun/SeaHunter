package com.sea.user.activity.integral.mall

import android.content.Intent
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.integral.mall.detail.IntegralShopDetailActivity
import com.sea.user.activity.integral.shop.ExchangeShopActivity

class IntegralMallAdapter(mList: List<IntegralMallItem>) :
    BaseQuickAdapter<IntegralMallItem, BaseViewHolder>(R.layout.item_integral_mall_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: IntegralMallItem?) {
        helper?.getView<TextView>(R.id.tvNowRedeem)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ExchangeShopActivity::class.java))
        }
        helper?.getView<ConstraintLayout>(R.id.cVIntegralShop)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, IntegralShopDetailActivity::class.java))
        }
    }
}